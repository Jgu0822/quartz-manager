# QUARTZ-MANAGER REST API

이 프로젝트는 다중 모듈 Maven 프로젝트로 구성되어 있으며 Quartz-Manager API를 가져오고 Quartz Scheduler와 REST를 통해 상호 작용하기 위해 가져와야 하는 핵심 라이브러리인 quartz-manager-starter-api와 Quartz-Manager API에 대한 기본 보안 레이어를 제공하는 quartz-manager-starter-security, Quartz Manager로 관리되는 Quartz Scheduler를 PostgreSQL 데이터베이스에 지속할 수 있게 하는 quartz-manager-starter-persistence 등의 라이브러리들로 구성되어 있습니다.

This is a multi-module maven project.<br>
프로젝트 구조는 다음과 같습니다.

## PROJECT STRUCTURE
* `quartz-parent/quartz-manager-starter-api` is the core library must be imported to get the Quartz-Manager API and to interact with the [Quartz Scheduler](http://www.quartz-scheduler.org/) via REST.<br>
Quartz-Manager API를 가져오기 위한 핵심 라이브러리로서 Quartz Scheduler와 REST를 통해 상호 작용합니다.
* `quartz-parent/quartz-manager-starter-security` is a library that can be imported to get an out-of-the-box security layer over the quartz-manager API.<br>
Quartz-Manager API에 대해 미리 구성된 보안 레이어를 제공하는 라이브러리입니다.
* `quartz-parent/quartz-manager-starter-persistence` is a library that can be imported to persist the Quartz Scheduler managed by Quartz Manager, in a Postgresql database.<br>
Quartz Manager로 관리되는 Quartz Scheduler를 PostgreSQL 데이터베이스에 지속하는 라이브러리입니다.
* `quartz-parent/quartz-manager-starter-ui`  is a maven module with all the logic to build and package the angular frontend in a webjar.<br>
Angular 프론트엔드를 웹자로 빌드하고 패키징하는 데 필요한 모든 로직이 포함된 Maven 모듈입니다.
* `quartz-parent/quartz-manager-web-showcase` is nothing but a simple backend which imports the above libraries, helpful to develop with a frontend started locally with the webpack dev server.<br>
위의 라이브러리들을 가져오는 단순한 백엔드 모듈로, 로컬에서 웹팩 개발 서버로 시작한 프론트엔드를 개발하는 데 유용합니다.
* `quartz-frontend` is the angular single-page-app that interacts with the Quartz Manager API.<br>
Quartz Manager API와 상호 작용하는 Angular 기반의 싱글 페이지 애플리케이션입니다.

## PROJECT DETAILS
**[requirements]** Make sure you have installed<br>
프로젝트를 로컬 환경에서 빌드하고 실행하려면 JDK 9 이상, Maven 3.6 이상, npm 16 이상, node 8 이상, 그리고 angular-cli가 설치되어 있어야 합니다.
* [JDK](https://java.com/download/) 9 or greater
* [Maven](https://maven.apache.org/) 3.6 or greater
* [npm](https://www.npmjs.com/get-npm) 16 or greater , [node](https://nodejs.org) 8 or greater
* [angular-cli](https://cli.angular.io/)

To build&run Quartz Manager locally in your machine:

```
#CLONE REPOSITORY - 프로젝트를 클론합니다
git clone https://github.com/fabioformosa/quartz-manager.git

# START QUARTZ-MANAGER-WEB - Quartz Manager 백엔드를 시작합니다
cd quartz-manager/quartz-parent
mvn install
cd quartz-manager/quartz-parent/quartz-manager-web-showcase
mvn spring-boot:run

# START QUARTZ-MANAGER-FRONTEND - Quartz Manager 프론트엔드를 시작합니다
cd quartz-manager/quartz-manager-frontend
npm install
npm start

```

1. Open browser at [http://localhost:4200](http://localhost:4200) <br>
  웹 브라우저를 열고 [http://localhost:4200](http://localhost:4200)으로 접속합니다.
1. If you've imported `quartz-manager-security-starter` log in with **default credentials**: `admin/admin`<br>
만약 quartz-manager-security-starter를 가져왔다면, 기본 자격 증명(admin/admin)으로 로그인할 수 있습니다.

If you are not confident with maven CLI, you can start it by your IDE. For more details [spring boot ref.](http://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-running-your-application.html)<br>
만약 Maven CLI에 자신이 없다면, IDE로 시작할 수 있습니다. 더 자세한 내용은 [spring boot ref.](http://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-running-your-application.html)를 참고하세요.


## HOW TO RUN YOUR SCHEDULED JOB - 작업 예약하기
By default, `quartz-manager-web-showcase` executes the dummy job that logs "hello world!".
Replace the dummy job (class: `it.fabioformosa.quartzmanager.jobs.SampleJob`) with yours. Follow these steps:<br>
기본적으로 quartz-manager-web-showcase는 "hello world!"를 기록하는 더미 작업을 실행합니다. 이를 여러분의 작업으로 바꾸려면 다음 단계를 따릅니다:

1. Extend the super class `it.fabioformosa.quartzmanager.jobs.AbstractLoggingJob`<br>
it.fabioformosa.quartzmanager.jobs.SampleJob를 여러분의 작업으로 대체합니다.
1. set property `quartz-manager.jobClassPackages` with the list of the java packages (comma separated) containing the job class eligible for Quartz Manager<br>
여러분의 작업 클래스가 Quartz Manager에서 인식되도록 하려면, 해당 클래스가 속한 자바 패키지를 quartz-manager.jobClassPackages 속성에 지정합니다.

## HOW TO CHANGE SETTINGS - 설정 변경하기
* Num of Threads: `/quartz-manager-parent/quartz-manager-web/src/main/resources/managed-quartz.properties`<br>
스레드 수 변경: /quartz-manager-parent/quartz-manager-web/src/main/resources/managed-quartz.properties 파일에서 설정합니다.
* Credentials: To change admin's password, set app property (or ENV var) `quartz-manager.security.accounts.in-memory.users[0].passord`<br>
자격 증명 변경: admin의 비밀번호를 변경하려면 quartz-manager.security.accounts.in-memory.users[0].passord 속성을 수정합니다.
* quartz-manager backend context path (default `/quartz-manager`) and port (default `8080`): `/quartz-manager/src/main/resources/application.properties`<br>
Quartz Manager 백엔드 컨텍스트 경로 (기본값: /quartz-manager) 및 포트 (기본값: 8080) 변경: /quartz-manager/src/main/resources/application.properties 파일에서 설정합니다.

## Tech Overview - 기술 개요

**Backend Stack** Java 9, Spring Boot 2.5.6 (Spring MVC 5.3.12, Spring Security 5.5.3), Quartz Scheduler 2.3.2<br>
백엔드 스택: Java 9, Spring Boot 2.5.6 (Spring MVC 5.3.12, Spring Security 5.5.3), Quartz Scheduler 2.3.2

**Frontend** Angular 14.2.12, Web-Socket (stompjs 2.3.3)<br>
프론트엔드: Angular 14.2.12, Web-Socket (stompjs 2.3.3)

**Style** Angular Material 14, FontAwesome 5<br>
스타일: Angular Material 14, FontAwesome 5

Starting from Quartz Manager v2.x.x, the new structure of project is:
* Multi-module maven project: REST API backend
* Angular 14: Single Page Application frontend<br>
Quartz Manager v2.x.x부터 새로운 프로젝트 구조로 변경되었으며,
REST API 백엔드와 Angular 14 기반의 단일 페이지 애플리케이션 프론트엔드로 구성됩니다.

(The first version of quartz manager was a monolithic backend that provided also frontend developed with angularjs 1.6.x. You can find it at the branch 1.x.x)
(첫 번째 버전의 Quartz Manager는 간단한 백엔드를 제공하며, AngularJS 1.6.x로 개발된 프론트엔드도 포함하는 모놀리식 구조였습니다. 이 버전은 브랜치 1.x.x에서 찾을 수 있습니다.)

## Contributes

Every contribution is welcome. Open a github's issue, let's discuss about the new features and how to implement them.
