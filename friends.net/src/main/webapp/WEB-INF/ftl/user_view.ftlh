<#ftl encoding="UTF-8"/>
<#import "header.ftlh" as base>
<link rel="stylesheet" href="/static/css/user.css">
<@base.main>
    <style>
        .photoEv {
            width: 100%;
            height: 100%;
            object-fit: contain;
        }

        .mainEv {
            display: flex;
            padding: 60px 0 0;
            flex-direction: column;
        }

        .eventEv {
            width: 100%;
            display: flex;
            margin-bottom: 20px;
            align-items: center;
            background: black;
        }

        .nameEv {
            font-family: 'Ravi Prakash', cursive;
            color: black;
            text-decoration: none;
            transition: color 0.4s linear;
        }

        .nameEv:hover {
            color: lightsalmon;
        }

        .descrEv {
            font-family: "Roboto", sans-serif;
            align-content: center;
            width: 70%;
            color: white;
        }

        .h_pEv {
            width: 30%;
            height: auto;
            display: flex;
            flex-direction: column;
            align-items: center;
        }

        .imgEv {
            width: 100%;
            height: 150px;
        }
    </style>


    <div class="profile">
        <div class="container">
            <div class="photo">
                <#if image??>
                    <img class="img" src="/img?id=${image.id}" alt=" ">
                </#if>
            </div>
            <div class="inf">
                <div class="name">
                    <div class="name_text">
                        ${fullName}
                    </div>
                </div>
                <div class="space">
                    <h3>О себе:</h3>
                </div>
                <div class="about">
                    <div class="about_user">
                        <#if description??>
                            ${description}
                        </#if>
                    </div>
                </div>
            </div>
        </div>
        <div class="container">

            <#if user.username==username>
                <form action="/UpdateProfile" style="float: right">
                    <button>Редактировать профиль</button>
                </form>

                <form action="/UserEvents" style="float: right">
                    <button>Мои мероприятия</button>
                </form>

            </#if>

        </div>
        <br>
        <br><#if user.username==username>
        <H3>Предстоящие мероприятия</H3>
        <#if list??>
            <#list list as event>

                <div class="mainEv">
                    <a class="nameEv" href="/Event?id=${event.id}">${event.name}</a>
                    <div class="eventEv">
                        <div class="h_pEv">
                            <div class="imgEv">
                                <#if event.image??>
                                    <img class="photoEv" src="/img?id=${event.image.id}">
                                </#if>
                            </div>
                        </div>
                        <div class="descrEv">
                            ${event.description}
                        </div>
                        <div class="descrEv">-->
                            <p>${event.status}</p>

                        </div>
                        <div class="descrEv">-->
                            <p>${event.date}</p>

                        </div>
                        <form method="post" action="/Profile">
                            <input type="hidden" name="event_id" value="${event.id}">
                            <button type="submit" class="btn btn-secondary">
                                Удалить
                            </button>
                        </form>
                    </div>
                </div>
            </#list></#if></#if>

    </div>
</@base.main>
