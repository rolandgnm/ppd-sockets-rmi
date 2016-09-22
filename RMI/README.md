# Vira Letras
### Roland Gabriel
#### Julho/2016
[Projeto](https://github.com/rolandgnm/ppd/blob/master/RMI/Projeto-Fase2-RMI.pdf)

###Jogo dual player em rede via Java RMI
#### MVC + Java Swing + RMI

##Run
- Download [jar/RMI.jar](https://github.com/rolandgnm/ppd/raw/master/RMI/jar/RMI.jar)
- Extrair RMI.jar:
```{r, engine='bash', count_lines} 
$ jar xf RMI.jar
```
- Em /folder contendo RMI.jar e os aquivos extraídos (pasta /folder/br/) executar num terminal:
```{r, engine='bash', count_lines} 
$ rmiregistry
```
- Em 2 novas instâncias do terminal executar:
```{r, engine='bash', count_lines}
$ java -cp RMI.jar br.viraletras.RMI.Main 
```

##Tools
* NetBeans 8.1 (Constução da interface) 
* IntelliJ IDEA CE 2016.1.3 (finalização) 
* Java 8 (jdk1.8.0_92.jdk)

###### especial thanks to [Derek Banas](http://www.newthinktank.com/2013/02/mvc-java-tutorial/)



