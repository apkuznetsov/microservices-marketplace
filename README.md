```
# to build the code as a docker image, open a command-line window and execute the following command:
$ mvn clean package dockerfile:build

# to start the docker image, stay in the directory containing the source code and run the following command: 
$ docker-compose -f docker/docker-compose.yml up
```

![](about/chat-general.png)

![](about/controller-preorder.png)

![](about/controller-product.png)

![](about/controller-category.png)

![](about/controller-auth-and-activation.png)

![](about/controller-seller-and-customer.png)

![](about/controller-cashback.png)
