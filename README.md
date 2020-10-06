
## Pré-requisitos

Java Runtime Environment, Java SE Development Kit e Maven.



# Instalação

  ### Windows:
    Caso esteja no Windows siga estas instruçõs: https://dicasdejava.com.br/como-instalar-o-maven-no-windows/.
    Ou execute o arquivo ``grafo.jar`` disponivel diretamente.
    
  ### Linux:
```
$ sudo apt update
```
```
$ sudo apt install default-jre
```
```
$ sudo apt install default-jdk
```
```
$ sudo apt install maven
```

## Execução:

Dentro da pasta "ProjetoLMD" no terminal, execute: 
```
$ mvn install
```

```
$ mvn exec:java -Dexec.mainClass="com.ufu.ProjetoLMD.ProjetoLMD"
```
Ou execute o arquivo ``grafo.jar`` disponivel.

![alt text](https://github.com/PedruuH/ProjetoLMD/blob/main/image.PNG?raw=true)
