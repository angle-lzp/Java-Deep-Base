# 大模型专有名词&基础知识

## 1. `google/gemma-4-31B-it-qat-w4a16-ct` 中 `B` 的含义

> **B = Billion，十亿**

`31B` 表示 **31 Billion 参数，约 310 亿参数**。

Gemma4-31B 的精确参数量约为 30.7B，命名时取整为 31B。

> 注意：这里的 **B 不是 Byte（字节）**，而是参数量的计数单位，和显存里的 GB 不是一回事。

### 1.1 权重显存估算公式

权重显存的基础估算公式：

$$
\operatorname{权重显存(GB)} = \frac{\text{参数量} \times \text{每参数占用 bit}}{8 \times 1024^3}
$$

其中：

- `8 bit = 1 Byte`
- `1 GB = 1024^3 Byte`

### 1.2 原始 BF16（16bit，未量化）

BF16 下每个参数占 `16bit = 2 Byte`。

$$
31 \times 10^9 \times 2 \div 1024^3 \approx \mathbf{57.9\ GB}
$$

谷歌官方给出的 Gemma4 31B BF16 显存约为 **69.9GB**。多出的部分主要来自 embedding、norm 层等额外张量开销。

### 1.3 w4a16 权重部分（不含 KV Cache）

w4a16 中权重为 4bit，每个参数占 `4bit = 0.5 Byte`。

$$
31 \times 10^9 \times 0.5 \div 1024^3 \approx \mathbf{14.5\ GB}
$$

谷歌官方实测权重部分约为 **17GB**。原因是 embedding、LN 层等部分通常仍保留 BF16，不会全部进行 4bit 量化。

### 1.4 总显存组成

大模型推理时的显存主要由三部分组成：

1. **模型权重**：静态部分，模型加载后基本固定。
2. **KV Cache**：上下文对话中的 key/value 缓存，会随上下文长度线性增长。
3. **额外开销**：激活张量、驱动、框架和系统预留等。

总显存可粗略估算为：

$$
\operatorname{总 VRAM} \approx \text{模型权重显存} + \text{KV Cache} + \text{10\% 到 20\% 系统预留}
$$

### 1.5 KV Cache 简易计算公式

#### 基础公式

对 Transformer Decoder 架构（Gemma / Llama / Qwen 等）来说，KV Cache 显存可以用下面公式估算：

$$
\operatorname{KV Cache(Byte)} = 2 \times \text{batch\_size} \times \text{seq\_len} \times \text{num\_heads} \times \text{head\_dim} \times \text{bytes\_per\_element}
$$

#### 符号说明

| 符号 | 含义 |
|---|---|
| `2` | K 矩阵 + V 矩阵，两份缓存 |
| `batch_size` | 并发请求数 |
| `seq_len` | 上下文总长度，即 prompt + 已生成 token 总数 |
| `num_heads` | 注意力头总数 |
| `head_dim` | 每个注意力头的维度 |
| `bytes_per_element` | KV 缓存存储精度。FP16/BF16 为 2 字节，FP8/INT8 为 1 字节 |

因为模型隐藏维度满足：

$$
\operatorname{hidden\_size} = \text{num\_heads} \times \text{head\_dim}
$$

所以公式也可以简写为：

$$
\operatorname{KV Cache(Byte)} = 2 \times \text{batch\_size} \times \text{seq\_len} \times \text{hidden\_size} \times \text{bytes\_per\_element}
$$

转换为 GB：

$$
\operatorname{KV Cache(GB)} = \frac{\text{KV Cache(Byte)}}{1024^3}
$$

### 1.6 代入 Gemma4-31B 参数

Gemma4-31B 的关键参数：

- `hidden_size = 8192`
- KV Cache 默认通常使用 BF16/FP16，即 `bytes_per_element = 2`

代入后：

$$
\operatorname{KV Cache(GB)} = \frac{2 \times batch \times seq\_len \times 8192 \times 2}{1024^3}
$$

化简为：

$$
\operatorname{KV Cache(GB)} \approx batch \times seq\_len \times \mathbf{0.0000305176}
$$

#### batch = 1 时的示例

| `seq_len` | KV Cache 估算 |
|---:|---:|
| 4,096 | $1 \times 4096 \times 0.0000305176 \approx \mathbf{0.125\ GB}$ |
| 32,768 | $1 \times 32768 \times 0.0000305176 \approx \mathbf{1.0\ GB}$ |
| 131,072（128K） | $1 \times 131072 \times 0.0000305176 \approx \mathbf{4.0\ GB}$ |
| 262,144（256K） | $1 \times 262144 \times 0.0000305176 \approx \mathbf{8.0\ GB}$ |

> 结论：Gemma4 31B 在 `batch = 1`、BF16 KV Cache、256K 上下文时，KV Cache 大约占用 **8GB 显存**。

### 1.7 重要工程注意事项

1. **vLLM / PagedAttention**

	vLLM 使用分页 KV 缓存，不会一次性预分配全部显存，而是按需申请。但最大占用上限仍然可以用上面的公式估算。

2. **KV Cache INT8 / FP8 量化**

	如果 `bytes_per_element` 从 2 变为 1，KV Cache 显存会直接减半。

	例如：Gemma4 31B、`batch = 1`、256K 上下文、INT8 KV Cache 时，KV Cache 约为 **4GB**。

3. **batch > 1 时线性增长**

	例如：`batch = 4`、256K 上下文、BF16 KV Cache 时，KV Cache 约为 `4 × 8GB = 32GB`。这也是长上下文多并发非常吃显存的原因。

4. **模型量化不影响 KV Cache 大小**

	w4a16 只表示权重量化；KV Cache 精度是独立配置，默认可能仍然是 FP16/BF16。

### 1.8 汇总估算总显存

$$
\operatorname{Total VRAM} \approx \text{模型权重显存} + \text{KV Cache 显存} + \text{框架/驱动预留(1 到 3GB)}
$$

## 2. `1 PFLOP FP4` 与 `2070 TOPS` 与 `1.3 TFLOPS`

### 2.1 单位对齐

- **1 PFLOP FP4**：每秒约 $10^{15}$ 次 FP4 浮点运算。
- **2070 TOPS**：每秒约 $2.07 \times 10^{15}$ 次整数或 AI 操作。TOPS 中的 T 通常表示 tera，即 $10^{12}$，所以 `2070 TOPS = 2.07 × 10^15 ops/s`。
- **1.3 TFLOPS**：每秒最多执行约 $1.3 \times 10^{12}$ 次浮点运算。

### 2.2 `1.3 TFLOPS` 的含义

`1.3 TFLOPS` 可以拆开理解：

- **T** = Tera = $10^{12}$，万亿
- **FLOPS** = Floating Point Operations Per Second，浮点运算每秒
- **1.3 TFLOPS** = $1.3 \times 10^{12}$ 次浮点运算/秒

也就是说：

$$
1.3\ \text{TFLOPS} = 1.3\ \text{TOPS}
$$

但这个等式只在“单位数量级”上成立，不代表实际计算能力完全等价。

### 2.3 FLOPS 和 TOPS 的关系

| 单位 | 全称 | 通常表示 | 常见数据类型 |
|---|---|---|---|
| FLOPS | Floating Point Operations Per Second | 浮点运算能力 | FP32、FP16、BF16、FP8、FP4 |
| TOPS | Tera Operations Per Second | 每秒万亿次操作 | INT8、INT4、NPU/AI 加速操作，也可能泛指 AI ops |

### 2.4 怎么对比

关键要看指标后面的 **数据类型、计算口径和硬件路径**。

| 指标 | 能否直接比 | 说明 |
|---|---|---|
| 1.3 TFLOPS FP32 vs 1.3 TOPS INT8 | 不能直接比 | 一个是 32-bit 浮点，一个是 8-bit 整数 |
| 1.3 TFLOPS FP16 vs 1.3 TOPS INT8 | 不能直接比 | 精度、硬件单元、模型支持都不同 |
| 1.3 TFLOPS FP4 vs 1.3 TOPS INT4 | 勉强可做粗略量级比较 | 但仍要看硬件实现和稀疏性 |
| 1.3 TFLOPS FP32 vs 2.6 TFLOPS FP32 | 可以直接比 | 同架构/同精度下，2.6 理论上约 2 倍 |
| 100 TOPS INT8 vs 200 TOPS INT8 | 可以直接比 | 同精度、同计算口径下，200 理论上约 2 倍 |

> 简单说：只有在相同精度、相同计算口径、相同硬件路径下，TFLOPS / TOPS 的数字才适合公平比较。

### 2.5 一句话总结

**TFLOPS 和 TOPS 在数学单位上都表示“每秒多少万亿次操作”，但 TFLOPS 通常指浮点运算，TOPS 通常指整数或 AI 加速操作。只有在相同精度、相同计算口径、相同硬件路径下，才能公平对比。**

### 2.6 `1 PFLOP FP4` 与 `2070 TOPS` 的数字规模

只看数字大小：

$$
1\ \text{PFLOP} = 1000\ \text{TFLOPS}
$$

$$
2070\ \text{TOPS} = 2070\ \text{TOPS}
$$

因此，**2070 TOPS 的理论操作次数大约是 1 PFLOP 的 2.07 倍**。

### 2.7 为什么不能直接比较

不能简单地说 Jetson Thor 一定比 DGX/RTX Spark 更强，因为两者统计口径和适用场景不同。

| 指标 | 1 PFLOP FP4 | 2070 TOPS |
|---|---:|---:|
| 数字规模 | 1000 万亿次/秒 | 2070 万亿次/秒 |
| 数值类型 | FP4，4-bit 浮点 | 多半是 INT8 / 稀疏 AI TOPS，具体看 NVIDIA 标注口径 |
| 更适合 | 大模型推理、低精度 Transformer、LLM | 边缘 AI、机器人、多传感器感知、视觉推理 |
| 是否能直接比较 | 不完全能 | 不完全能 |

### 2.8 结论

如果只按每秒操作次数的峰值数字看，**2070 TOPS 比 1 PFLOP FP4 大，约为 2.07 倍**。

但从实际 AI 能力看，**1 PFLOP FP4 对大模型/LLM 更有参考价值**。FP4 是 Blackwell 架构中面向低精度生成式 AI 的重要格式；而 **2070 TOPS 更偏 Jetson Thor 这类边缘机器人平台的 AI 推理峰值**，优势在功耗、实时性、I/O、机器人和视觉生态，而不是桌面级大模型容量。

更直观地说：

- 跑机器人视觉、多摄像头感知、边缘推理：**Jetson Thor 的 2070 TOPS 很强**。
- 跑本地大语言模型、agent、长上下文、微调：**1 PFLOP FP4 + 大统一内存的平台更合适**。
- 单纯比纸面峰值：**2070 TOPS 数字更大**。
- 比谁更能跑大模型：还要看 **内存容量、内存带宽、支持的数据格式、软件栈**，不能只看 TOPS/PFLOPS。

## 3. 模型计算和存储时常用的数字格式

### 3.1 常见格式对比

| 格式 | 每个参数占用 | 特点 |
|---|---:|---|
| FP32 | 4 字节 | 精度高，训练常用，但很占内存 |
| FP16 | 2 字节 | 半精度，推理和训练都常见，省一半内存 |
| BF16 | 2 字节 | 半精度，动态范围比 FP16 更大，训练更稳定 |
| INT8 | 1 字节 | 8-bit 量化，省内存，可能有少量精度损失 |
| INT4 / W4 | 0.5 字节 | 4-bit 量化，非常省内存，精度损失更明显但推理很实用 |
| FP4 | 0.5 字节 | 4-bit 浮点，Blackwell 架构重点支持 |

以 31B 参数模型为例：

```text
FP32:          31B × 4 bytes   ≈ 124GB
FP16 / BF16:  31B × 2 bytes   ≈ 62GB
INT8:          31B × 1 byte    ≈ 31GB
INT4 / W4 / FP4: 31B × 0.5 byte ≈ 15.5GB
```

### 3.2 FP16 和 BF16 的作用

#### 作用一：减少显存和内存占用

FP32 每个参数占 4 字节，FP16/BF16 每个参数占 2 字节，因此可以直接节省约一半内存。

例如：

```text
31B FP32       约 124GB
31B FP16/BF16  约 62GB
```

#### 作用二：提高推理速度

NVIDIA GPU 的 Tensor Core 对 FP16/BF16 有专门加速，所以 FP16/BF16 通常比 FP32 快很多。

#### 作用三：保持比 INT4/INT8 更高的数值精度

FP16/BF16 仍然是浮点格式，通常比 INT4/INT8 量化模型更接近原始模型效果。

代价是：FP16/BF16 的内存占用比 4-bit 量化模型大很多。

### 3.3 FP16 与 BF16 的区别

| 项目 | FP16 | BF16 |
|---|---|---|
| 全称 | Floating Point 16 | Brain Floating Point 16 |
| 大小 | 16 bit | 16 bit |
| 内存占用 | 一样 | 一样 |
| 数值范围 | 较小 | 更接近 FP32，范围更大 |
| 精度细腻度 | 比 BF16 更细 | 比 FP16 粗一点 |
| 稳定性 | 训练时可能更容易溢出 | 训练更稳定 |
| 推理 | 很常用 | 也很常用 |

### 3.4 一句话总结

**FP16/BF16 是 16-bit 半精度浮点格式，用来在尽量保持模型效果的同时减少内存、加快计算；但它仍然比 4-bit 量化模型占用大约 4 倍内存。**