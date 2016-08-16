/*
 * 简单的上下翻页
 * 
 */
if (typeof jQuery === 'undefined') {
    throw new Error('ylzpnpage requires jQuery');
}

(function($){

	var YlzPNPage = function( pnpageDIV, options ){
		this.options = options;
		this.$pnpageDIV = pnpageDIV;
		this._init();
	}
	
	YlzPNPage.prototype={
		constructor:YlzPNPage,
		_init:function(){
			if( this.options.data != undefined ){
				this._loadData( this.options.data, null );
			}
			this.currentPage = 1;
			this.totalPage = this.$pnpageDIV.find(".ylzpnpage").length;
			this.$pnpageDIV.find(".ylzpnpage").hide();
			this.$pnpageDIV.find(".ylzpnpage[pageNo=1]").show();
			var nav = $("<nav>");
			var ul = $("<ul class='pager'>");
			nav.append(ul);
			var preLi = $("<li class='previous'><a href='#'><span aria-hidden='true'>&larr;</span>前一页</a></li>");
			var nextLi = $("<li class='next'><a href='#'>后一页 <span aria-hidden='true'>&rarr;</span></a></li>");
			ul.append(preLi).append(nextLi);
			nextLi.click( function(){
				$(this).closest(".ylzparentpnpage").ylzPNPage("nextPage");
			});
			preLi.click( function(){
				$(this).closest(".ylzparentpnpage").ylzPNPage("previousPage");
			});
			preLi.hide();
			if( this.totalPage == 1 ){
				nextLi.hide();
			}
			this.$pnpageDIV.append(nav);
			var yzp=this;
			
			if( this.options.validator != undefined ){
				this.options.validator.on("success.form.bv",function(e){
						if( yzp.currentPage == yzp.totalPage ){
							console.log( "success" );
							if( typeof yzp.options.validatorSuccess == 'function' ){
								yzp.options.validatorSuccess();
							} else {
								var $form = $(e.target);
								$form[0].submit();
							}
							yzp._firstPage();
						} else {
							console.log( "nextPage" );
							yzp._nextPage();
						}
					});
			}
			
		},
		_firstPage:function(){
			this.$pnpageDIV.find(".ylzpnpage[pageNo="+ this.currentPage+"]").hide();
			this.currentPage = 1;
			this.$pnpageDIV.find(".ylzpnpage[pageNo="+this.currentPage+"]").show();
			$(".previous").hide();
		},
		_loadData:function( data, name ){
			for( var key in data ){
				if( name == null ){
					name = key;
				} else {
					name = name + "." + key;
				}
				if( typeof data[key] != object ){
					var input = this.$pnpageDIV.find("input[name="+name+"]");
					if( input.length > 0 ){
						input.each(function(){
							input.val( data[key] );
						});
					}
					
				} else if( typeof data[key] == object ){
					this._loadData( data[key], name );
				}
			}
		},
		_nextPage:function(){
			console.log("current:" + this.currentPage + " totalPage:" + this.totalPage );
			if( this.currentPage < this.totalPage ){
				this.$pnpageDIV.find(".ylzpnpage[pageNo="+ this.currentPage+"]").hide();
				this.currentPage += 1;
				this.$pnpageDIV.find(".ylzpnpage[pageNo="+this.currentPage+"]").show();
				if( this.currentPage == this.totalPage ){
					$(".next").hide();
				}
				$(".previous").show();
			}
		},
		nextPage:function(){
			if( this.options.validator != undefined ){
				$(this.options.validator[0]).bootstrapValidator("validate")
			} else {
				this._nextPage();
			}
		},
		previousPage:function(){
			console.log("current:" + this.currentPage + " totalPage:" + this.totalPage );
			if( this.currentPage > 1 ){
				this.$pnpageDIV.find(".ylzpnpage[pageNo="+this.currentPage+"]").hide();
				this.currentPage -= 1;
				this.$pnpageDIV.find(".ylzpnpage[pageNo="+this.currentPage+"]").show();
				if( this.currentPage == 1 ){
					$(".previous").hide();
				}
				$(".next").show();
			}
		}
	}
	
	$.fn.ylzPNPage = function( option ){
		var data = $(this).data('ylzPNPage'),
			options = typeof option == 'object' && option;
		var internal_return;
		if ( !data ) {
	        data = new YlzPNPage(this, options);
	        $(this).data('ylzPNPage', data);
	    } else if( typeof option == 'object' ) {
			data.options = options;
		}
		
		if( typeof option == 'string' && typeof data[option] == 'function' ){
			internal_return = data[option].apply(data);
		}
		
		if( internal_return != undefined ) {
			return internal_return;
		}
		return data;
	}
	
})(jQuery);