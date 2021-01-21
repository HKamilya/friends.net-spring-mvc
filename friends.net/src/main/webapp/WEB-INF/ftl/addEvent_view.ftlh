<#ftl encoding="UTF-8"/>
<#import "header.ftlh" as base>

<@base.main>
    <#if user??>
        <div style="background: #6b6b6b; height: 100%">
            <br>
            <br>
            <form name="form" action="/AddEvent" method="post" enctype="multipart/form-data">
                <table align="center">
                    <tr>
                        <td>Название</td>
                        <td><label>
                                <input type="text" name="name" id="name" required/>
                            </label></td>
                    </tr>
                    <tr>
                        <td>Город</td>
                        <td><label>
                                <input type="text" name="city" id="city" required/>
                            </label></td>
                    </tr>
                    <tr>
                        <td>Улица</td>
                        <td><label>
                                <input type="text" name="street" id="street"/>
                            </label></td>
                    </tr>
                    <tr>
                        <td>Дом</td>
                        <td><label>
                                <input type="text" name="house" id="house"/>
                            </label></td>
                    </tr>
                    <tr>
                        <td>Дата</td>
                        <td><label>
                                <input type="date" name="date" id="date" required/>
                            </label></td>
                    </tr>
                    <tr>
                        <td>Время</td>
                        <td><label>
                                <input type="time" name="time" id="time" required/>
                            </label></td>
                    </tr>
                    <tr>
                        <td>Фотография</td>
                        <td><label>
                                <input type="file" name="image" accept="image/*" id="image" required/>
                            </label></td>
                    </tr>
                    <tr>
                        <td>Описание</td>
                        <td>
                            <label>
                                <textarea rows="5" name="description" id="description" required></textarea>
                            </label>
                        </td>
                    </tr>
                    <tr>
                        <td>Категория</td>
                        <td>
                            <label>
                                <select name="category" id="category"><#list list as categories>
                                        <option name="category_id" value="${categories.id}">${categories.name}</option>
                                    </#list>
                                </select>
                            </label>
                        </td>
                    </tr>
                    <tr>
                        <td><#if errMessage??>
                                ${errMessage}
                            </#if>
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td><input type="submit" value="Добавить">
                            <input type="reset" value="Сбросить"></td>
                    </tr>
                </table>
            </form>
        </div>
    <#else >
        <div onload="location.href='/Login'"></div>
    </#if>
</@base.main>