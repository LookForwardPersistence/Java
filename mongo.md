# mongo 操作
- 创建集合
~~~
db.createCollection('myData')
~~~
- 查看集合
~~~
show collections
~~~
- 插入文档
~~~
db.myData.insert({'key':'value'})
~~~
- 查看集合全部数据
~~~
#横向展开
db.myData.find（）
竖向展开
db.myData.find().pretty()
~~~
- 搜索指定文档
~~~
db.myData.find({'key':'value'})
~~~
- 更新文档
~~~
#更新符合条件的一条数据
db.myData.update({'key':'value'},{$set:{'key':'v2'}})
#更新符合条件的所有数据
db.myData.update({'key':'value'},{$set:{'key':'v2'}},{multi:true})
~~~
- 删除文档
query :（可选）删除的文档的条件。
justOne : （可选）如果设为 true 或 1，则只删除一个文档，如果不设置该参数，或使用默认值 false，则删除所有匹配条件的文档。
writeConcern :（可选）抛出异常的级别
~~~
db.myData.remove(
   <query>,
   {
     justOne: <boolean>,
     writeConcern: <document>
   }
)
#删除集合所有数据
db.myData.remove({})
~~~

- 删除集合
~~~
db.myData.drop()
~~~
