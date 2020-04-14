~~~
1、测试公众号申请：
https://mp.weixin.qq.com/debug/cgi-bin/sandboxinfo?action=showinfo&t=sandbox/index
2、测试公众号自定菜单配置：
https://mp.weixin.qq.com/debug/cgi-bin/apiinfo?t=index

{
    "button": [
        {
            "type": "view", 
            "name": "候选人注册", 
            "url": "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxa8840b96983bc5c2&redirect_uri=https://ebus.csot.tcl.com/rwp/wechat/register&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect"
        }, 
        {
            "name": "菜单", 
            "sub_button": [
                {
                    "type": "view", 
                    "name": "搜狗一下", 
                    "url": "http://www.soso.com/"
                }, 
                {
                    "type": "view", 
                    "name": "视频", 
                    "url": "http://v.qq.com/"
                }, 
                {
                    "type": "view", 
                    "name": "百度一下", 
                    "url": "https://www.baidu.com"
                }
            ]
        }
    ]
}
~~~
