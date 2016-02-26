window.QRLogin = {};
window.code = 408;
jQuery(function(){
  
   
    function init() {
        //changeQrcode();
        // add by xusheng on 04/26
      //  $(".pay_bill .pay_bill_unit:last-child").addClass("no_extra");

        var _nTimer = 0,
            _oGuide$ = $("#guide"),
            _oGuideTrigger$ = $("#QRcode");

        function _back() {
            _nTimer = setTimeout(function() {
                _oGuide$.stop().animate({marginLeft:"-101px",opacity:0}, "400", "swing",function(){
                    _oGuide$.hide();
                });
            }, 100);
        }

        /*guide*/
        _oGuide$.css({"left":"50%", "opacity":0});
        _oGuideTrigger$.mouseover(function(){
            clearTimeout(_nTimer);
            _oGuide$.css("display", "block").stop().animate({marginLeft:"+147px", opacity:1}, 900, "swing", function() {
                _oGuide$.animate({marginLeft:"+134px"}, 300);
            });
        }).mouseout(_back);

        _oGuide$.mouseover(function(){
            clearTimeout(_nTimer);
        }).mouseout(_back);
    }

    init();
});
