import json
import time
import threading
from http.server import BaseHTTPRequestHandler, HTTPServer


class LapiEnums:
    RESPONSECODE = 0
    STATUSCODE = 0
    RESPONSESTRING = "Succeed"

    # 接口定义
    LAPI_REGISTER = "/LAPI/V1.0/System/UpServer/Register"
    LAPI_KEEPALIVE = "/LAPI/V1.0/System/UpServer/Keepalive"
    LAPI_UNREGISTER = "/LAPI/V1.0/System/UpServer/Unregister"
    LAPI_SUBSCIPTION = "/LAPI/V1.0/System/Event/Subscription"
    LAPI_DELETE_SUBSCIPTION = "/LAPI/V1.0/System/Event/Subscription/0"
    LAPI_NOTIFICATION = "/LAPI/V1.1/System/Event/Notification"
    LAPI_LINERULEDATA = "/LAPI/V1.0/System/Event/Notification/PeopleCount/LineRuleData"
    LAPI_AREARULEDATA = "/LAPI/V1.0/System/Event/Notification/PeopleCount/AreaRuleData"
    LAPI_RULEDATA = "/LAPI/V1.0/System/Event/Notification/ByWayDetection/RuleData"
    LAPI_HEALTHDATA = "/LAPI/V1.0/System/Event/Notification/HealthData"
    LAPI_REALHEALTHDATA = "/LAPI/V1.0/System/Event/Notification/RealHealthData"
    LAPI_OBJECTREALTIMEDATA = "/LAPI/V1.0/System/Event/Notification/ObjectRealTimeData"

    # 文件保存路径
    BASE_PATH = "E:\\tool\\雷视\\雷视人存demo需求\\人存雷达demo\\file"


class HttpRequestHandler(BaseHTTPRequestHandler):
    def _set_response(self, status=200):
        self.send_response(status)
        self.send_header("Content-Type", "application/json; charset=UTF-8")
        self.end_headers()

    def do_POST(self):
        content_length = int(self.headers.get("Content-Length", 0))
        request_body = (
            self.rfile.read(content_length).decode("utf-8")
            if content_length > 0
            else ""
        )
        client_ip = self.client_address[0]
        print(f"收到请求: {self.path}, 来自: {client_ip}, 请求体: {request_body}")

        keepAliveRsp = None
        type_str = ""
        # 根据 URI 分发不同请求
        if self.path == LapiEnums.LAPI_KEEPALIVE:
            print(f"收到来自 {client_ip} 的保活请求")
            keepAliveRsp = {"timeout": 60, "timestamp": int(time.time())}
            type_str = "保活请求参数为"
        elif self.path == LapiEnums.LAPI_NOTIFICATION:
            print(f"接收到来自 {client_ip} 的告警数据和事件数据: {request_body}")
            type_str = "告警数据和事件数据"
        elif self.path == LapiEnums.LAPI_LINERULEDATA:
            print(f"接收到来自 {client_ip} 的绊线统计周期数据: {request_body}")
            type_str = "绊线统计周期数据"
        elif self.path == LapiEnums.LAPI_AREARULEDATA:
            print(f"接收到来自 {client_ip} 的区域统计周期数据: {request_body}")
            type_str = "区域统计周期数据"
        elif self.path == LapiEnums.LAPI_RULEDATA:
            print(f"接收到来自 {client_ip} 的途经统计周期数据: {request_body}")
            type_str = "途经统计周期数据"
        elif self.path == LapiEnums.LAPI_HEALTHDATA:
            print(f"接收到来自 {client_ip} 的康养周期数据: {request_body}")
            type_str = "康养周期数据"
        elif self.path == LapiEnums.LAPI_REALHEALTHDATA:
            print(f"接收到来自 {client_ip} 的实时康养数据: {request_body}")
            type_str = "实时康养数据"
        elif self.path == LapiEnums.LAPI_OBJECTREALTIMEDATA:
            print(f"接收到来自 {client_ip} 的目标实时数据: {request_body}")
            type_str = "目标实时数据"
        else:
            print("未知请求URI，返回404")
            self._set_response(404)
            self.wfile.write(
                json.dumps({"error": "Not Found"}, ensure_ascii=False).encode("utf-8")
            )
            return

        # 构造响应，与 Java 中的 HttpResponse 类似
        response = {
            "Response": {
                "ResponseURL": self.path,
                "ResponseCode": LapiEnums.RESPONSECODE,
                "ResponseString": LapiEnums.RESPONSESTRING,
                "StatusCode": LapiEnums.STATUSCODE,
                "Data": keepAliveRsp
            }
        }
        response_json = json.dumps(response, ensure_ascii=False)
        print(f"响应: {response_json}")
        self._set_response(200)
        self.wfile.write(response_json.encode("utf-8"))

    def do_GET(self):
        self.send_response(404)
        self.end_headers()


class PythonHttpServer:
    def __init__(self, ip="127.0.0.1", port=8082):
        self.server_address = (ip, port)
        self.httpd = HTTPServer(self.server_address, HttpRequestHandler)
        self._server_thread = None

    def start(self):
        print(
            f"正在启动HTTP服务器: IP={self.server_address[0]}, Port={self.server_address[1]} ......"
        )
        self._server_thread = threading.Thread(
            target=self.httpd.serve_forever, daemon=True
        )
        self._server_thread.start()
        print("HTTP服务器启动成功")

    def stop(self):
        print("正在关闭HTTP服务器...")
        self.httpd.shutdown()
        self.httpd.server_close()
        if self._server_thread:
            self._server_thread.join()
        print("HTTP服务器关闭")


if __name__ == "__main__":
    server = PythonHttpServer(ip="127.0.0.1", port=8082)
    try:
        server.start()
        # 保持主线程运行，可以根据需要调整停止条件
        while True:
            time.sleep(1)
    except KeyboardInterrupt:
        server.stop()
