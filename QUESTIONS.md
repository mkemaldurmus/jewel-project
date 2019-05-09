**Please view this document at https://stackedit.io. Just copy contents and paste it in left pane.**

---
##### Question 1: Project is separated to 3 modules. What is the aim of creating multiple modules for a project? Explain.
##### Answer:Çünkü böl yönet(Divide and conquer) tekniği burda işimizi kolaylaştırmaktadır yani projeyi bir takım olararak yapacağımız zaman hem iş bölümü hemde projeyi anlamak daha kolay olur.
---
##### Question 2: In root module's `pom.xml` file, we defined dependencies between`<dependencyManagement></dependencyManagement>` tags. Child modules dependencies has no version and wrote between `<dependency></dependency>` tags. What is difference between `<dependencyManagement>` and `<dependency>` tags? Explain.
##### Answer:dependencyManagement Dev ortamında kullandığınız bağımlılıkların aynı versiyonunun üretimde kullanılanın aynı olduğundan emin olurlar yani bağımlılıklarınızın en son yama, sürüm veya ana sürümle güncellenmesini sağlar.dependency ise yazdığımız sürümü kullanır
---
##### Question 3: What are Kafka's `replication-factor` and `partitions`? Why we can't increase number of partitions and replica in single broker? Explain.
##### Answer: Replication factor  cluster'da kaç kopyada tutulacağını belirtir bizim verilerimizin kayıp olmaması içindir örneğin bir tane cluster çöktüğünde replication factor 3 ise verimizi diğer cluster'dan alabiliriz partition ise kaç bölüme ayrılacağını belirtir.Çünkü biz farklı makinelere bağanmadık localhosta çalıştık eğer farklı makinelrimiz olsaydı partition sayısını artırırdık.
 
##### Question 4: Why can't we run Kafka without Zookeeper? Explain.
##### Answer:Çünkü Zookeeper arka planda kaynak yönetimini sağlar ve dağıtık sunucu kümeleri üzerindeki işleri koordine etmeye yardımcı olur yani zookeeper kafkanin herşeyidir.
---
##### Question 5: Increasing the parallelism parameter of Apache Spark does not increase Kafka's consumer parallelism. Explain why.
##### Answer: spark distbuted çalısır ama kafka consumer disturbuted çalısmaz kafkanın kendisi distbuted çalısır
---
##### Question 6: In `TrainerMain` class, after loading data from MongoDB with Spark, it doesn't comes as a `RDD` class but as `Dataset<Row>`at line 37. Explain why.
##### Answer:
---
##### Question 7: In `TrainerMain` class there are few variables constructed from`StringIndexer` class.  What is the aim of `StringIndexer` class? Do we must to use it? Explain.
##### Answer:
---
##### Question 8: In `TrainerMain` class there is a variable called `oneHotEncoderEstimator` which is constructed from `OneHotEncoderEstimator`. What is the aim of `OneHotEncoderEstimator` class?  Explain.
##### Answer:
---
##### Question 9: In `TestMain` class there is a variable called `metrics` which is constructed from `RegressionMetrics`. What is the aim of `RegressionMetrics` class?  Explain.
##### Answer:
---
##### Question 10: In `TestMain` class there are few `println` commands to write regression metrics to screen. Replace metrics output value to zero value below:
##### Answer:
| Parameter |Output
|--|--|
|R2| 0 |
|Root Mean Squared Error| 0 |
|Mean Absolute Error| 0 |
|Mean Squared Error| 0 |