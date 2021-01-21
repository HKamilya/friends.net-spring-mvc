<#ftl encoding="UTF-8"/>
<#import "header.ftlh" as base>
<link rel="stylesheet" href="/static/css/style_menu_alternative.css">
<@base.main>

    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.css"/>

    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Ravi+Prakash&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
          integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
            integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
            crossorigin="anonymous"></script>
    <script type="text/javascript" src="https://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"
            integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI"
            crossorigin="anonymous"></script>
    <link rel="stylesheet" href="/static/css/style_menu_alternative.css">
    <link rel="stylesheet" href="/static/css/search.css">
    <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
    <script>

        $(function () {
            $('#button').click(function (event) {
                var search = $('#search').val();
                console.log(search);
                // var elements = document.forms['filter'].elements['tags'];
                // var len = elements.length;
                var checkboxes = document.getElementsByName('cat');
                var tags = [];
                i = 0;
                for (var index = 0; index < checkboxes.length; index++) {
                    if (checkboxes[index].checked) {
                        tags[i] = checkboxes[index].value;
                        i++;
                    }
                }
                var date = $('#date').val();

                $.ajax({
                    type: "POST", url: "/Search",
                    data: {
                        search: search,
                        tags: JSON.stringify(tags), // все чекбоксы в массиве
                        date: date
                    },
                    dataType: 'json',
                    success: function (result) {
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
        });
    </script>
    <title>List</title>

    <div class="main-content"><p class="headline"></p>
        <div class="container">
            <div class="row">
                <div class="col-sm-4"><p><b>Фильтры</b></p>
                    <form id="filter" name="filter" autocomplete="off">
                        <div class="autocomplete">
                            <input class="search" accept="" name="search" id="search"
                                   placeholder="Поиск по названию..." type="text">
                        </div>
                        <br>
                        <table>
                            <th>По категории:</th>
                            <br>
                            <#list catList as catList>
                                <tr>
                                    <td>
                                        <div class="form-check form-check-inline">
                                            <input class="form-check-input" type="checkbox" name="cat" id="category"
                                                   value="${catList.id}">
                                            <label class="form-check-label" for="outside">${catList.name}</label>
                                        </div>
                                    </td>
                                </tr>
                            </#list>

                            <th>По дате:</th>
                            <tr>
                                <td>
                                    <label>
                                        <input type="date" name="date" id="date">
                                    </label>
                                </td>
                            </tr>
                        </table>
                        <input class="sub" id="button" type="button" value="Поиск" style="margin-bottom: 5px">
                    </form>
                </div>
                <div class="col-sm-8" style="border-left: 1px solid black">

                    <div class="main">
                        <#include "allEv_view.ftlh">
                        <#if message??>
                            <h2>${message}</h2>
                        </#if>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script>
        function autocomplete(inp, arr) {
            var currentFocus;
            inp.addEventListener("input", function (e) {
                var a, b, i, val = this.value;
                closeAllLists();
                if (!val) {
                    return false;
                }
                currentFocus = -1;
                a = document.createElement("DIV");
                a.setAttribute("id", this.id + "autocomplete-list");
                a.setAttribute("class", "autocomplete-items");
                this.parentNode.appendChild(a);
                for (i = 0; i < arr.length; i++) {
                    if (arr[i].substr(0, val.length).toUpperCase() == val.toUpperCase()) {
                        /*create a DIV element for each matching element:*/
                        b = document.createElement("DIV");
                        b.innerHTML = "<strong>" + arr[i].substr(0, val.length) + "</strong>";
                        b.innerHTML += arr[i].substr(val.length);
                        b.innerHTML += "<input type='hidden' value='" + arr[i] + "'>";
                        b.addEventListener("click", function (e) {
                            inp.value = this.getElementsByTagName("input")[0].value;
                            closeAllLists();
                        });
                        a.appendChild(b);
                    }
                }
            });
            inp.addEventListener("keydown", function (e) {
                var x = document.getElementById(this.id + "autocomplete-list");
                if (x) x = x.getElementsByTagName("div");
                if (e.keyCode == 40) {
                    currentFocus++;
                    addActive(x);
                } else if (e.keyCode == 38) {
                    addActive(x);
                } else if (e.keyCode == 13) {

                    e.preventDefault();
                    if (currentFocus > -1) {

                        if (x) x[currentFocus].click();
                    }
                }
            });

            function addActive(x) {
                if (!x) return false;
                removeActive(x);
                if (currentFocus >= x.length) currentFocus = 0;
                if (currentFocus < 0) currentFocus = (x.length - 1);
                x[currentFocus].classList.add("autocomplete-active");
            }

            function removeActive(x) {
                for (var i = 0; i < x.length; i++) {
                    x[i].classList.remove("autocomplete-active");
                }
            }

            function closeAllLists(elmnt) {
                var x = document.getElementsByClassName("autocomplete-items");
                for (var i = 0; i < x.length; i++) {
                    if (elmnt != x[i] && elmnt != inp) {
                        x[i].parentNode.removeChild(x[i]);
                    }
                }
            }

            document.addEventListener("click", function (e) {
                closeAllLists(e.target);
            });
        }

        var names = ${names};
        autocomplete(document.getElementById("search"), names);
    </script>

</@base.main>