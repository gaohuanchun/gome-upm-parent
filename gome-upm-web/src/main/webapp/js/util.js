var util = {
		service : {
			//判断是不是数字
			isNum : function(num){
				var flag = false;
				var reg = new RegExp("^[1-9]\d*$"); 
				if(reg.test(num)){
					return true;
				}
				return flag;
			}
		}
}


Array.prototype.contains = function(obj) {
    var i = this.length;
    while (i--) {
        if (this[i] === obj) {
            return true;
        }
    }
    return false;
}