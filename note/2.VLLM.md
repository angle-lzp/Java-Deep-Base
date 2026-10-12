## VLLM 指令

### 1.查看当前 vLLM 服务暴露的模型名
```shell
curl http://localhost:8000/v1/models
```

### 2.健康检查
```shell
curl http://localhost:8000/health
```

### 3.Prometheus 指标
```shell
curl http://localhost:8000/metrics

# 查看请求耗时、队列、token、GPU cache
curl http://localhost:8000/metrics | grep vllm
```

### 4.vLLm请求测试
```shell
# 测试文本请求
curl -X POST http://localhost:8000/v1/chat/completions \
  -H "Content-Type: application/json" \
  --data '{
    "model": "Qwen/Qwen3.5-9B",
    "messages": [
      {
        "role": "user",
        "content": "你是谁？"
      }
    ],
    "temperature": 0,
    "max_tokens": 128
  }'

# 测试图片请求
curl -X POST http://localhost:8000/v1/chat/completions \
  -H "Content-Type: application/json" \
  --data '{
    "model": "Qwen/Qwen3.5-9B",
    "messages": [
      {
        "role": "user",
        "content": [
          {
            "type": "text",
            "text": "Describe this image in one sentence."
          },
          {
            "type": "image_url",
            "image_url": {
              "url": "https://cdn.britannica.com/61/93061-050-99147DCE/Statue-of-Liberty-Island-New-York-Bay.jpg"
            }
          }
        ]
      }
    ],
    "temperature": 0,
    "max_tokens": 128
  }'
```
