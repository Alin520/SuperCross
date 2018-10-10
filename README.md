# SuperCross
  让跨进程间通信变得轻而易举

# 使用步骤:
## 1、引入库文件  
    implementation project(':cross')
## 2、在清单文件Manifest.xml中，声明要用到的Service
    如：<service android:name="com.alin.cross.core.CrossService"></service>
## 3、服务端预注册类信息
    Cross.getDefault().register(UserInfo.class);
### 4、客户端需要做的事情：
 ### (1)连接远程服务
      Cross.getDefault().connect(this, CrossService.class);
      注意：这里的CrossService便是清单文件中声明的service。
      
  ### (2)获取需要访问服务端进程中的对象
     IUserInfo mIUserInfo = Cross.getDefault().getInstance(IUserInfo.class);
     
  ### (3)根据获取到的对象，调用对象中的方法
     mIUserInfo.setUser(new User("CallActivity张三",888888));
     mIUserInfo.setAddress("来自CallActivity...地址");
     
  [点击查看源码分析](https://blog.csdn.net/hailin123123/article/details/82998495)
