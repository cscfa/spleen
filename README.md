# spleen
Java cache library

Spleen is a storage cache library writen in java that allow a storage memory size limit and also a time out limit for each stored data.

Spleen offer two storage type, that used simple thread or multi-thread removing observer.

The simple namespace start an alone thread to check the data to remove. The usage of this namespace is a good practice for common cache. The acccess on write/read are fast but on very hight object cahe usage the remover thread can take more time to delete the outdated objets or to limit memory usage.

The multi-threaded namespace will create multiples containers objects and attach a remover thread for each of them. This practice offer to remove more efficiently the outdated objects or limit memory usage.
