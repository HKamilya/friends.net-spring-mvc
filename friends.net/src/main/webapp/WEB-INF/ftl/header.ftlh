<#macro main css=[]>
    <html>
    <head>
        <link rel="stylesheet" type="text/css"
              href="https://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.css"/>

        <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300&display=swap" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Ravi+Prakash&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
              integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk"
              crossorigin="anonymous">

        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
              integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z"
              crossorigin="anonymous">
<#--        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"-->
<#--                integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"-->
<#--                crossorigin="anonymous"></script>-->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script type="text/javascript"
                src="https://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
                integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
                crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"
                integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI"
                crossorigin="anonymous"></script>
        <link rel="stylesheet" type="text/css"
              href="https://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.css"/>
        <link rel="stylesheet" type="text/css"
              href="https://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.css"/>


        <meta charset="UTF-8">
        <link rel="stylesheet" href="/static/css/style_menu.css">
        <link rel="stylesheet" href="/static/css/style.css">
    </head>
    <body>
    <header class="header">
        <div class="container_main">
            <div class="header__inner">
                <div class="hl">Friends.net</div>
                <nav class="nav">
                    <#if user??>
                        <a class="nav__link" href="/AddEvent">Организовать <br>
                            мероприятие</a>
                    </#if>
                    <a class="nav__link" href="/Search">Поиск</a>
                    <div class="header__logo">
                        <img class="picture" src="/static/img/man.png" alt="">
                    </div>
                    <a class="nav__link" href="/AllEvents?page=0">Интересные<br>мероприятия</a>
                    <a class="nav__link" href="/RandomEvent">Случайное<br>
                        мероприятие</a>
                </nav>
                <div class="header__auth">

                    <#if !user??>
                        <a class="auth__link" href="/Registration">Регистрация</a>
                        <a class="auth__link" href="/Login">Войти</a>
                    <#else >
                        <a class="auth__link" href="/Profile">${user.username}</a>
                        <a href="/Logout">Выйти</a>
                    </#if>
                </div>
            </div>
        </div>

    </header>
    <#nested >
    </body>
    </html>
</#macro>