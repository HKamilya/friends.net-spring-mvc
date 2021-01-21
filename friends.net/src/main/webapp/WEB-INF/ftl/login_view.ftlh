<#ftl encoding="UTF-8"/>
<#import "header.ftlh" as base>

<@base.main>
    <link rel="stylesheet" href="/static/css/login.css">

    <title>Login</title>
    <div class="container">
        <div class="row align-items-center helper">
            <div class="col-12 top"></div>
            <div class="col-3">
            </div>
            <div class="col-6 login_block">
                <div class="col-12 top2"></div>
                <form method="post" action="/Login" name="form">
                    <div class="row">
                        <div class="col-2"></div>
                        <div class="col-8">
                            <label for="usernm">Логин</label>
                            <br>
                            <input type="text" <#if username??>value="${username}" </#if> class="form-control"
                                   id="username" aria-describedby="emailHelp"
                                   name="username">
                            <br>
                            <br>
                            <label for="pass">Пароль</label>
                            <br>
                            <input type="password" class="form-control" id="password"
                                   <#if password??>value="${password}" </#if> name="password">
                            <br>
                            <br>
                            <input type="checkbox" onclick="validate()" class="form-check-input"
                                   id="rememberMe2">
                            <input type="hidden" name="rememberMe" id="rememberMe" value="false">
                            <label class="form-check-label" for="exampleCheck1">Запомнить меня</label>
                            <br>
                            <span style="color:red">
                                <#if errMessage??>${errMessage}</#if></span>
                            <br>
                            <input type="submit" id="button" class="btn btn-secondary btn-lg"
                                   value="Войти">
                            <input type="reset" class="btn btn-secondary btn-lg" value="Сбросить">
                        </div>
                        <div class="col-2"></div>
                    </div>
                </form>
            </div>
            <div class="col-3">
            </div>
        </div>
    </div>
    <script>
        function validate() {
            var checkbox = document.getElementById('rememberMe2');
            if (checkbox.checked) {
                document.getElementById('rememberMe').value = true;
            }
        }
    </script>
</@base.main>