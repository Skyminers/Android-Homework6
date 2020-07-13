# Chapter-6
存储

   由于不知道如何在ViewHolder中进行数据库操作，所以采用了传入回调函数的方式来操作数据库
   对于手动添加新数据则是为了整齐，将对数据库的操作写在同一文件中

   过程中遇到了不少的BUG，，主要的BUG就是RecyclerView复用机制带来数据错乱
   因为以前一直不知道RecyclerView的复用可能会带来数据错乱，调试了很久
   直到详细打印log之后，才发觉OnCheckedChangeListener()函数被大量且错误地异常调用。上网搜索了相关资料才直到RecyclerView的复用机制会导致CheckBox的OnCheckedChangeListener出现十分异常的效果
   最终采用了Onclick方式，才终于调试通过

   程序运行截图：

![截屏2020-07-14 上午1.27.33.png](https://i.loli.net/2020/07/14/VI1ABUSCaLvMJkP.png)
![截屏2020-07-14 上午1.27.54.png](https://i.loli.net/2020/07/14/alYKQLH6TEg2jxe.png)