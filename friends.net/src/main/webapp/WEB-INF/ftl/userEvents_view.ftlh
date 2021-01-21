<#ftl encoding="UTF-8"/>
<#import "header.ftlh" as base>
<link rel="stylesheet" href="/static/css/userEvents.css">
<@base.main>
    <#list evReqList as event, request>

        <div class="main">
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
                    ${event.description}
                </div>
                <div class="descr">-->
                    <p>${event.status}</p>
                    <form action="/UserEvents" method="post">
                        <input type="hidden" name="event_id" value="${event.id}">
                        <select name="status">
                            <option name="status_name" value="1">актуально</option>
                            <option name="status_name" value="2">неактуально</option>
                        </select>
                        <button type="submit" name="submit">Сохранить</button>
                    </form>
                </div>


                <button type="submit" class="btn btn-primary" data-toggle="modal"
                        data-target="#event${event.id}">
                    Список идущих
                </button>
            </div>
        </div>
        <div class="modal fade" id="event${event.id}" tabindex="-1" role="dialog"
             aria-labelledby="exampleModalLongTitle"
             aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">

                        <#list request as req>
                            <a href="/AnProfile?username=${req.subscriber_id.username}">${req.subscriber_id.fullname}</a>
                            <br>
                            <#if req.comment??>
                                <p>${req.comment}</p>
                            </#if>

                        </#list>

                    </div>
                </div>
            </div>
        </div>
    </#list>
</@base.main>