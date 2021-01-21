<link rel="stylesheet" href="/static/css/allEv.css">

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
