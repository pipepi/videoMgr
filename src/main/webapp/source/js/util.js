//验证QQ号码5-11位
function isQQ(qq) {
    var filter = /^\s*[.0-9]{5,11}\s*$/;
    if (!filter.test(qq)) {
        return false;
    } else {
        return true;
    }
}

//验证邮箱格式
function isEmail(str) {
    if (str.charAt(0) == "." || str.charAt(0) == "@" || str.indexOf('@', 0) == -1 ||
        str.indexOf('.', 0) == -1 || str.lastIndexOf("@") == str.length - 1 ||
        str.lastIndexOf(".") == str.length - 1 ||
        str.indexOf('@.') > -1)
        return false;
    else
        return true;
}

//校验手机号码
function isMobil(s) {
    //var patrn = /^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/;
    var patrn = /^(13[0-9]{9})|(14[0-9])|(18[0-9])|(15[0-9][0-9]{8})$/;
    if (!patrn.exec(s)) return false
    return true
}
function tip(id,c){
	$('#'+id).text(c);
	$('#'+id).show();
}
