function getXmm(){
		
		var ssz = $("#parentSelect option:selected").text();
		//alert(ssz);
		
		$.ajax({
			url:contextPath+'/system/getSystemXmm',
			type:"POST",  
	        dataType: 'json',	
			data:{"ssz":ssz},
			success:function(data){
				/*console.info(data);
				var bool = data.indexOf("sessionTimeOut");
				if(bool < 0){
					$(".list_table").empty();
					$(".list_table").append(data);
					var sele = $("#select");
					sele.length=1;
					for(var i=0;i<data.length;i++){    
						sele[i+1]=new Option(data[i],data[i]);    
		                }
					
				}else{
					window.location.href=contextPath+"/home";
				}*/
				//alert(data);
				var sele = $("#select");
				sele.empty();
				
				if(data.length>0){
					for(var i=0;i<data.length;i++){    
						//sele.options[i+1]=new Option(data[i],i);
						if(i==0){
							var option = $("<option>").text("请选择").val("请选择");
							sele.append(option);
						}
						var option = $("<option>").text(data[i]).val(data[i]);
						sele.append(option);
		            }
				}else if(data==null){
					var option = $("<option>").text("请选择").val("请选择");
					sele.append(option);
				}else{
					var option = $("<option>").text("请选择").val("请选择");
					sele.append(option);
				}
				
			},
			error:function(){
				alert("操作失败");
				
			}
			
			
		});
		
	}