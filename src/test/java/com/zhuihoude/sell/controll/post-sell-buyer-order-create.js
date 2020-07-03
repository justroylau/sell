// 没有Post 测试工具，就拿Console 来凑合凑合吧
var httpRequest = new XMLHttpRequest();//第一步：创建需要的对象
httpRequest.open('POST', 'http://127.0.0.1/sell/buyer/order/create', true); //第二步：打开连接
httpRequest.setRequestHeader("Content-type","application/x-www-form-urlencoded");//设置请求头 注：post方式必须设置请求头（在建立连接后设置请求头）
httpRequest.send('name=测试名称&phone=测试号码&address=测试地址&openid=测试OpenId&items=[{productId:"1",productQuantity:0}]');//发送请求 将情头体写在send中
/**
 * 获取数据后的处理程序
 */
httpRequest.onreadystatechange = function () {//请求后的回调接口，可将请求成功后要执行的程序写在其中
    if (httpRequest.readyState == 4 && httpRequest.status == 200) {//验证请求是否发送成功
        var json = httpRequest.responseText;//获取到服务端返回的数据
        console.log(json);
    }
};
