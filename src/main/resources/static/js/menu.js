$(document).ready(function(){

    // 마우스 가져가면 목차 펼치기
    $(".menu>a").mouseover(function(){
        $(this).next("ul").slideDown();
    });

    // 마우스 빼면 목차 접기
    $(".menu>a").mouseout(function(){
        $(this).next("ul").slideUp();
    });

});