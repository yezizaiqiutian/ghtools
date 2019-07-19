# ghtools

工具类,以后逐步完善

## netlib

借鉴于:https://github.com/wzgiceman/RxjavaRetrofitDemo-master

上面框架提供了许多扩展的功能,比如使用greendao缓存网络数据等等
有一个不方便的地方:每个接口都需要建立相应的api管理类,虽然可以高度定制每个接口的参数,但是使用会相对麻烦

该demo目的是一个新的项目需要一个简单的网络框架,以后比较好扩展,所以
- 去掉了许多暂时不用的功能,
- 新增了通用的api管理类
- 统一版本控制
- 将版本库与项目参数尽量分离

使用:

```
    NetUtils.getNet(this,simpleOnNextListener);
    
    //回调一一对应
    HttpOnNextListener simpleOnNextListener = new HttpOnNextListener<List<SubjectResulte>>() {
    
        //请求接口及参数
        @Override
        public Flowable onConnect(HttpPostService service) {
            return service.getAllVedioBys(true);
        }

        //请求成功
        @Override
        public void onNext(List<SubjectResulte> subjects) {
            tvMsg.setText("网络返回：\n" + subjects.toString());
        }

        //请求失败
        @Override
        public void onError(Throwable e) {
            super.onError(e);
            tvMsg.setText("失败：\n" + e.toString());
        }

        //请求取消
        @Override
        public void onCancel() {
            super.onCancel();
            tvMsg.setText("取消請求");
        }
    };
```





