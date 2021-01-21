<#ftl encoding="UTF-8"/>
<#import "header.ftlh" as base>

<@base.main>
    <link rel="stylesheet" href="/static/css/register.css">
    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

    <script>
        function validate() {
            var fullname = document.form.fullname.value;
            var email = document.form.email.value;
            var username = document.form.username.value;
            var password = document.form.password.value;
            var conpassword = document.form.conpassword.value;

            if (fullname == null || fullname == "") {
                alert("Имя не должно быть пустым");
                return false;
            } else if (email == null || email == "") {
                alert("Введите адрес электронной почты");
                return false;
            } else if (username == null || username == "") {
                alert("Введите логин");
                return false;
            } else if (password.length <= 6) {
                alert("Пароль должен состоять минимум из 6 символов");
                return false;
            } else if (password != conpassword) {
                alert("Пароли должны совпадать");
                return false;
            }
        }
    </script>

    <div class="container_reg">
        <div class="rectangle">
            <div class="register_form" style="text-align: center">
                <form name="form" action="Registration" method="post" onsubmit="return validate()">
                    <label class="txt" for="fullnm"><b>Имя</b></label>
                    <div class="register_line">
                        <input type="text" id="fullnm" name="fullname"/>

                    </div>
                    <label class="txt" for="email"><b>Адрес электронной почты</b></label>
                    <div class="register_line">
                        <input type="email" id="email" name="email"/>
                    </div>
                    <label class="txt" for="usernm"><b>Логин</b></label>
                    <div class="register_line">
                        <input type="text" id="usernm" pattern="^[a-zA-Z](.[a-zA-Z0-9_-]*)" name="username"/>
                        <span class="form-error">Неправильная форма логина</span>
                    </div>
                    <label class="txt" for="pass"><b>Пароль</b></label>
                    <div class="register_line">
                        <input type="password" id="pass"
                               name="password"
                               pattern="^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\s).*$"/>
                        <span class="form-error">Неправильная форма пароля</span>
                        <br>
                        <span style="color: white">Пароль должен содержать cтрочные и прописные латинские буквы, цифры</span>
                    </div>
                    <label class="txt" for="confPass"><b>Повторите пароль</b></label>
                    <div class="register_line">
                        <input type="password" id="confPass"
                               name="conpassword"/>
                    </div>
                    <p style="color: red"><#if errMessage??> ${errMessage}</#if>
                    </p>
                    <input type="submit" class="btn btn-secondary" value="Зарегистрироваться"/>
                </form>
            </div>
        </div>
    </div>
</@base.main>
