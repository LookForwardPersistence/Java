~~~
# 数据库 index songs_v1
PUT songs_v1

#type对数据逻辑划分
PUT /songs_v1/_mapping/popular
{
  "properties": {
    "songName":{
      "type": "text"
    },
    "singer":{"type": "text"},
    "lyris":{"type": "text"}
  }
}

#索引数据到es
POST /songs_v1/popular
{
  "songName":"Come on!",
  "singer":"Dawn1",
  "lyris":"Diana you are great！ Dawn come on!",
  "age":20
}

# 按key查找
GET /songs_v1/_search?q=singer:dawn

GET /songs_v1/_search?q=lyris:you



# 添加别名
PUT /songs_v1/_alias/songs

# 索引别名查询
GET /songs/_search?q=lyris:on

GET /songs/_search
{
  "query": {
    "match": {
      "lyris": "Diana you are great！ Dawn come on!"
    }
  }
}
# 且查询
GET /songs/_search
{
  "query": {
    "match": {
      "lyris": {
        "query": "Diana you are great！ Dawn come on!",
        "operator": "and",
        "fuzziness": 1 
      }
    }
  }
}

#multi_match
GET /songs/_search
{
  "query": {
    "multi_match": {
      "query": "dawn dinana",
      "fields": ["lyris","singer"],
      "operator": "or"
    }
  }
}

#common terms query
GET /songs/_search
{
  "query": {
    "common": {
      "lyris": {
        "query": "dinan great",
        "cutoff_frequency": 0.001,
        "low_freq_operator":"or",
        "high_freq_operator":"or",
        "minimum_should_match": {
          "high_freq": 2,
          "low_freq": 1
        }
      }
    }
  }
}

#query string query
GET /songs/_search
{
  "query": {
    "query_string": {
      "default_field": "lyris",
      "query": "dawn great"
    }
  }
}

#simple_query_string
GET /songs/_search
{
  "query": {
    "simple_query_string": {
      "query": "dawn+great",
      "fields": ["lyris","songName"]
    }
  }
}

GET /songs/_search
{
  "query": {
    "term": {
      "lyris.keyword": {
        "value": "dawn come"
      }
    }
  }
}
#tems
GET /songs/_search
{
  "query": {
    "terms": {
      "lyris": [
        "dawn",
        "dinana"
      ]
    }
  }
}

#>=20 <=30
GET /songs/_search
{
  "query": {
    "range": {
      "age": {
        "gte": 20,
        "lte": 30
      }
    }
  }
}
# age不为null
GET /songs/_search
{
  "query": {
    "exists": {
      "field": "age"
    }
  }
}
# 符合查询 age 为null
GET /songs/_search
{
  "query": {
    "bool": {
      "must_not": [
        {
          "exists": {
            "field": "age"
          }
        }
      ]
    }
  }
}

#prfix query 前缀查询
GET /songs/_search
{
  "query": {
    "prefix": {
      "singer.keyword": {
        "value": "Diana"
      }
    }
  }
}
#wildcard query 通配符查询
#? 代表单个字符 *或多个字符
GET /songs/_search
{
  "query": {
    "wildcard": {
      "singer": {
        "value": "D*"
      }
    }
  }
}

#regexp query 利用正则的查询

#添加新索引
PUT songs_v2

#将db同步songs_v2

#重新指向到songs_v2
POST /_aliases
{
  "actions": [
    {
      "remove": {
      "index": "songs_v1",
      "alias": "songs"
    },
      "add": {
        "index": "songs_v2",
        "alias": "songs"
      }
    }
  ]
}



GET /songs/_search


PUT songs_v3
#删除索引
DELETE songs_v3

#创建index 指定settings
PUT songs_v4
{
  "settings": {
    "number_of_shards": 5, 
    "number_of_replicas": 1
  }
}

GET /songs_v4/_settings

PUT /books

POST /books/_mapping/science
{
  "properties": {
    "name":{"type": "text"},
    "author":{"type": "text"}
  }
}

GET /books/_mapping

POST /books/science
{
  "day01":"2020-01-19"
}

#关闭时间匹配
POST /books/_mapping/science
{
  "date_detection": false
}

PUT /teacher
PUT /teacher/_mapping/math
{
 "dynamic":"strict",
 "properties": {
   "name":{"type": "text"},
   "gender":{"type": "boolean"}
 }
}

GET /teacher/_mapping


POST /teacher/math
{
  age:24
}

GET /teacher/_search



#term suggester
POST /songs/_search
{
  "suggest": {
    "my_SUGGESTION": {
      "text": "Diana",
      "term": {
        "FIELD": "body",
        "suggest_mode":"missing"
      }
    }
  }
}


PUT /blogs

POST /blogs/_search
{
  "suggest": {
    "MY_SUGGESTION": {
      "text": "luenec",
      "term": {
        "FIELD": "body"
      }
    }
  }
}


#select max(age) from songs
POST /songs/_search?size=0
{
  "aggs": {
    "max_age": {
      "sum": {
        "field": "age"
      }
    }
  }
}

#select count(*) from songs

POST /songs/_count
{
  "query": {
    "term": {
      "age": {
        "value": "30"
      }
    }
  }
}
#select count(*) from songs
POST /songs/_search?size=0
{
  "query": {
    "term": {
      "age": {
        "value": "30"
      }
    }
  },
  "aggs": {
    "age_count": {
      "value_count": {
        "field": "age"
      }
    }
  }
}

#占比统计
GET /songs/_search?size=0
{
  "aggs": {
    "age_percent": {
      "percentile_ranks": {
        "field": "age",
        "values": [
          10,
          20,
          30
        ]
      }
    }
  }
}

#percentiles
GET /songs/_search?size=0
{
  "aggs": {
    "TestName": {
      "percentiles": {
        "field": "age",
        "percents": [
          1,
          5,
          25,
          50,
          75,
          95,
          99
        ]
      }
    }
  }
}

#z指定缺失值处理策略
POST /songs/_search?size=0
{
  "aggs": {
    "avg_age": {
      "avg": {
        "field": "age",
        "missing": 0
      }
    }
  }
}

#桶聚合

GET /songs/_search?size=0
{
  "aggs": {
    "ages": {
      "terms": {
        "field": "age",
        "order": {
          "avg_age": "asc"
        }, 
        "size": 1
      },
  "aggs": {
    "avg_age": {
      "avg": {
        "field": "age"
      }
    }
  }
    }
  }
}
~~~
