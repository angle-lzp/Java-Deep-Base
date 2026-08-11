<!--
 * @Author: Angelo
 * @Date: 2026-03-12 11:49:02
 * @version: 
 * @Descripttion: 
-->
### 训练步骤
* 1.train       训练
* 2.test        测试
* 3.validation  验证

#### 1.分类训练
```shell
# 在 YOLO 的分类任务中，data 参数不需要 .yaml 配置文件，而是直接指向数据集的根目录。
# YOLO 会自动根据目录结构（即您提供的 train/empty, train/occupied 等）来识别类别。
# 分类模型训练（使用CPU训练）
yolo classify train \
    model=yolo11s-cls.pt \
    data=/home/jeffzhou/apps/dock_cls_project_yolo11s/dock_cls_dataset \
    epochs=100 \
    imgsz=384 \
    batch=16 \
    workers=4 \
    device=cpu \
    project=./runs/classify \
    name=dock_cpu_v1 \
    patience=10 \
    optimizer=AdamW \
    lr0=0.001


# 分类模型训练（使用GPU训练）- 离线训练建议的指令
# 针对现在“有 GPU、但没有网络”的情况，建议用下面这条离线训练命令：
yolo classify train \
  model=/home/jeffzhou/apps/dock_yolo11s_train/yolo11s-cls.pt \
  data=/home/jeffzhou/apps/dock_yolo11s_train/dock_cls_dataset \
  epochs=100 \
  imgsz=384 \
  batch=32 \
  workers=6 \
  device=0 \
  project=./ \
  name=dock_gpu_v0729 \
  patience=30 \
  optimizer=AdamW \
  lr0=0.001 \
  amp=False

# 如果你想让它更“稳妥”一点，尤其是离线环境，建议再加上：cache=False
yolo classify train \
  model=/home/jeffzhou/apps/dock_yolo11s_train/yolo11s-cls.pt \
  data=/home/jeffzhou/apps/dock_yolo11s_train/train0730/dock_cls_dataset \
  epochs=100 \
  imgsz=384 \
  batch=32 \
  workers=6 \
  device=0 \
  project=./ \
  name=dock_gpu_v0730 \
  patience=30 \
  optimizer=AdamW \
  lr0=0.001 \
  amp=False \
  cache=False

# 参数详解：
#         model=yolo11s-cls.pt: 指定使用 YOLO11s 分类模型。如果本地没有，它会自动下载。
#         data=dock_cls.yaml: 指向刚才创建的数据集配置文件。
#         epochs=100: 训练轮数。
#         imgsz=640: 输入图片尺寸（分类任务通常 224 或 640 均可，月台场景建议 640 以保留细节）。
#         batch=16: 批次大小。根据您的显存调整（AGX Orin 可以尝试 32 或 64，普通显卡 16 较稳）。
#         workers=8: 数据加载线程数。
#         patience=10: 早停机制，如果验证集指标 10 个 epoch 没提升则停止。
#         optimizer=AdamW: 优化器，分类任务常用。
#         lr0=0.001: 初始学习率。
#         project=./runs/classify: 训练结果保存路径。
#         name=dock_v1: 训练结果保存的文件夹名称。
#         device=0: 使用 GPU 训练。
#         amp=False: 是否使用自动混合精度训练。（false就不会连接网络进行AMP兼容性检查，适合无网训练）

# 分类模型测试
yolo classify predict model=./runs/classify/dock_v1/weights/best.pt source=path/to/your/image.jpg
```
