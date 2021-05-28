## 数据访问层十分重要
一种说法认为Data Access Object应该被替代为Repository,名称的变化实际是反应从关注数据到关心具体业务对象。
Repository应该是在数据层和业务逻辑层中间，它帮助从数据库、缓存甚至文件系统查询到的数据从数据源映射到业务实体，并将业务实体的更改保留到数据源。这样便可以将业务逻辑与与底层数据源或Web服务的交互分离，使得业务逻辑可以不受数据源更换的影响[^Repository]。

[^Repository]:[The Repository Pattern](https://docs.microsoft.com/en-us/previous-versions/msp-n-p/ff649690(v=pandp.10)?redirectedfrom=MSDN)