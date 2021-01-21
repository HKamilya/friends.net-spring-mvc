<#ftl encoding="UTF-8"/>
<#import "header.ftlh" as base>
<link rel="stylesheet" href="/static/css/style_menu_alternative.css">
<link rel="stylesheet" href="/static/css/allEv.css">
<@base.main>
    <script>
        $(function () {
            document.getElementsByName("page")[0].value = 0;
            document.getElementsByName("prevButton").forEach(e => e.style.visibility = "hidden")
            $('#button2').click(function (event) {
                var page = document.getElementsByName("page")[0].value;
                document.getElementsByName("page")[0].value = +page + +1;
                console.log(page)
                $.ajax({
                    type: "POST", url: "/AllEvents",
                    data: {
                        title: "next"
                    },
                    dataType: 'json',
                    success: function (result) {
                        if (result.length < 5) {
                            document.getElementsByName("nextButton").forEach(e => e.style.visibility = "hidden")
                        } else {
                            document.getElementsByName("nextButton").forEach(e => e.style.visibility = "visible")
                        }
                        $("#vstavka").detach();
                        $("<div id=\"vstavka\">").appendTo($("#v"));
                        for (var i = 0; i < result.length; i++) {
                            $('#vstavka').append($(' <a class=\"name\" href="/Event?id=' + result[i]['id'] + '">' + result[i]['name'] + '</a>' +
                                '<div class="event">' +
                                '  <div class="h_p">' +
                                '<div class="img">' +
                                ' <img  alt="" class="photo" src=/img?id=' + result[i]['image']['id'] + '>' +
                                '</div>' +
                                '</div>' +
                                '<div class="descr"><p>' + result[i]['description'] +
                                '</p>  <br><p>' + result[i]['date'] + '</p>' +
                                '           <br> <p>' + result[i]['time'] + '</p> <br><p>' + result[i]['category_id']['name'] + '</p>' +
                                '</div></div></div>'
                            ))
                        }
                        document.getElementsByName("prevButton").forEach(e => e.style.visibility = "visible")
                    }
                });
            });
            $('#button1').click(function (event) {
                var page = document.getElementsByName("page")[0].value;
                console.log(page);
                page = +page - +1;
                if (page === 0) {
                    document.getElementsByName("prevButton").forEach(e => e.style.visibility = "hidden")
                }
                document.getElementsByName("page")[0].value = page;
                $.ajax({
                    type: "POST", url: "/AllEvents",
                    data: {
                        title: "prev"
                    },
                    dataType: 'json',
                    success: function (result) {
                        if (result.length < 5) {
                            document.getElementsByName("nextButton").forEach(e => e.style.visibility = "hidden")
                        } else {
                            document.getElementsByName("nextButton").forEach(e => e.style.visibility = "visible")
                        }
                        $("#vstavka").detach();
                        $("<div id=\"vstavka\">").appendTo($("#v"));
                        for (var i = 0; i < result.length; i++) {
                            $('#vstavka').append($(' <a class=\"name\" href="/Event?id=' + result[i]['id'] + '">' + result[i]['name'] + '</a>' +
                                '<div class="event">' +
                                '  <div class="h_p">' +
                                '<div class="img">' +
                                ' <img  alt="" class="photo" src=/img?id=' + result[i]['image']['id'] + '>' +
                                '</div>' +
                                '</div>' +
                                '<div class="descr"><p>' + result[i]['description'] +
                                '</p>  <br><p>' + result[i]['date'] + '</p>' +
                                '           <br> <p>' + result[i]['time'] + '</p> <br><p>' + result[i]['category_id']['name'] + '</p>' +
                                '</div></div></div>'
                            ))
                        }
                    }
                });
            });
        })
    </script>
    <div class="main" id="v">
        <div id="vstavka">
            <#list list as event>
                <a class="name" href="/Event?id=${event.id}">${event.name}</a>
                <div class="event">
                    <div class="h_p">
                        <div class="img">
                            <#if event.image??>
                                <img class="photo" src="/img?id=${event.image.id}">
                            </#if>
                        </div>
                    </div>
                    <div class="descr">
                        <p>${event.description}</p>
                        <br>
                        <p>${event.date}</p>
                        <br><#if event.time??>
                        <p>${event.time}</p>
                    <br>
                        </#if>
                        <p>${event.category_id.name}</p>
                    </div>

                </div>
            </#list>
        </div>
    </div>
    <div class="col-12">
        <div class="col-10"></div>
        <div class="col-2" style="float: right">
            <input type="hidden" name="page">
            <button id="button1" name="prevButton" class="btn btn-secondary">Назад</button>
            <button type="button" name="nextButton" id="button2" class="btn btn-secondary">Далее</button>
        </div>
    </div>
</@base.main>