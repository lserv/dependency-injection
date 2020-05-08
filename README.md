# Dependency Injection
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](MIT)
[![Build Status](https://travis-ci.com/serveriev/dependency-injection.svg?branch=master)](https://travis-ci.com/serveriev/dependency-injection)

> Implementation of dependency injection in java. 

## Install

This package didn't upload to the maven central repository. Thus, for using this dependency you can use [Jitpack.io](https://jitpack.io/).

### 1. Add the Jitpack repository to pom.xml

```sh
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

### 2. Add the di dependency to pom.xml

```sh
<dependency>
    <groupId>com.github.serveriev</groupId>
    <artifactId>dependency-injection</artifactId>
    <version>v1.0</version>
</dependency>
```

## How to use

### 1. Configure your instances. 

You should create a Java file for initializing your instances(aka beans configuration in spring).

```java
import io.lenur.di.annotation.Dependencies;
import io.lenur.di.annotation.Instance;

@Dependencies
public class Instances {
    @Instance
    public AnswerDao getAnswerDai() {
        return new AnswerDao();
    }

    @Instance
    public QuestionDao getQuestionDao() {
        return new QuestionDao();
    }
}
```

### 2. Inject instances. 

```java
import io.lenur.di.annotation.Inject;

public class AnswerController {
    @Inject
    private AnswerDao answerDao;

    public boolean add(Answer answer) {
        return answerDao.add(answer);
    }

    public List<Answer> getAll() {
        return answerDao.getAll();
    }
}
```

### 3. Init dependencies scan and get your instances

```java
import io.lenur.di.Dependency;
import io.lenur.di.PackageContext;

public class Application {
    public static void main(String[] args) {
        PackageContext context = Dependency.init("io.example");
        AnswerController answerController = (AnswerController) context.getInstance(AnswerController.class);
        QuestionController questionController = (QuestionController) context.getInstance(QuestionController.class);
    }
}
```

## Example

See a [demo project](https://github.com/serveriev/dependency-injection-usage) for details

## Author

**Lenur**

* Github: [@serveriev](https://github.com/serveriev)

## Show your support

Give a ⭐️ if this project helped you!

## License

Copyright © 2020 [Lenur](https://github.com/serveriev).

This project is [MIT](MIT) licensed.