$(document).ready(function() {

    // 轮播滚动
    var mySwiper = $('.slidepics').swiper({
        loop : true,
        pagination: '.dotted p',
        paginationClickable: true,
        spaceBetween: 30,
        autoplay:3000,
        autoplayDisableOnInteraction:false,
        onSliderMove:function(swiper) {
            //如果光用此方法来申请app原生控件不拦截事件,在快速滑动的时候可能抢不到事件
            //还要配合下面的touch事件来获取
            isBeingDrag = true;
            window.Android.requestEvent(true);
        }
    });

    // 轮播滚动事件监听
    $('.slidepics').on('touchstart touchmove touchend touchcancel' , function(event) {
        var touch = event.originalEvent.targetTouches[0];
        switch (event.type) {
            case "touchstart":
                mLastClientX = touch.clientX;
                mLastClientY = touch.clientY;
                isBeingDrag = false;
                window.Android.requestEvent(true);
                break;
            case "touchmove":
                if (!isBeingDrag) {
                    var xDiff = Math.abs(touch.clientX - mLastClientX);
                    var yDiff = Math.abs(touch.clientY - mLastClientY);
                    //console.log(xDiff + "  " + yDiff);
                    if (xDiff >= touchSlop) {
                        isBeingDrag = true;
                    } else if(yDiff > touchSlop) {
                        //产生app纵向滑动,父控件不强制请求放行事件,这段逻辑主要是不影响外层垂直滑动控件的滑动
                        //js端控制滑动与app端的标准不一样,所以结合上面的onSliderMove:function方法来判断
                        //如果H5端控件已经产生滑动时则必须请求父控件放行事件
                        //如果有些开源控件没有类似onSliderMove方法时,只需提供控件是否产生滑动就行,原理都是一样
                        isBeingDrag = true;
                        window.Android.requestEvent(false);
                    }
                }
                break;
            case "touchend":
                window.Android.requestEvent(false);
                break;
        }
    });

});

//app端的滑动值
var touchSlop = 0;
//初次按下时的x, y轴值
var mLastClientX, mLastClientY;
//是否处于滑动中
var isBeingDrag = false;
//js附值,在web加载完成时将android的滑动单位值传给js
function initTouchSlop(appTouchSlop) {
    touchSlop = appTouchSlop;
}