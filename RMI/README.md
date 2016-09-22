# Vira Letras
### Roland Gabriel
#### Julho/2016
[Projeto](https://github.com/rolandgnm/ppd/blob/master/RMI/Projeto-Fase2-RMI.pdf)

###Jogo dual player em rede via Java RMI
#### MVC + Java Swing + RMI

##Run
1. Extrair RMI.jar para /folder.
    ```{r, engine='bash', count_lines} 
  $ jar xf RMI.jar
  ```
2. Em /folder contendo RMI.jar e os aquivos extraídos (pasta /br) executar num terminal:
  2.1. 
  ```{r, engine='bash', count_lines} 
  $ rmiregistry
  ```
  2.2. Em 2 novas instâncias do terminal executar:
  ```{r, engine='bash', count_lines}
  java -cp <.jar> br.viraletras.RMI.Main 
  ```

##Tools
* NetBeans 8.1 (Constução da interface) 
* IntelliJ IDEA CE 2016.1.3 (finalização) 
* Java 8 (jdk1.8.0_92.jdk)

###### especial thanks to [Derek Banas](http://www.newthinktank.com/2013/02/mvc-java-tutorial/)



