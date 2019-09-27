var options;
(function ($){
	var defaults = {
		title:null,				//上方的标题
		titleColor:'#999',
		titleFontSize:'13px',
		width:'100%',			//一共的宽度
		speed:1000,				//显示效果的时间（毫秒）
		hazard:80,				//预警值
		data:[],				//数据 json
		itemStyle:{
			fontSize:'12px',			//每条文字的大小
			bgColor:'#E6E6E6',			//每条的背景颜色
			borderColor:'#BCBCBC',		//边框线颜色
			normalBgColor:'#26A0DA',	//正常 百分比颜色
			waringBgColor:'#F50000'		//超过预警值 百分比颜色
		}
	};
	
	$.fn.disk = function (id,options){
		$(this).children().remove();
		options = $.extend(defaults,options);
		var dataObj = options.data;//数据JSON
		var o = this;
		var total=0;
		$.each(dataObj.disks,function (i,n){//统计总数
			total+=parseInt(n.value);
		});
		//alert(JSON.stringify($(this).find("<table>")));
		//$(this).find("<table>").remove();
		$(this).append("<table id='"+id+"' class='tb-disk-list' cellpadding='0' cellspacing='0' style='font-size:"+options.itemStyle.fontSize+";' width='"+defaults.width+"'></table>");//设置TABLE的长度
		//是否显示标题
		if(null != options.title){
			$("table",this).append("<tr><td colspan='3' align='left'><span style='color:"+options.titleColor+";font-size:"+options.titleFontSize+";'>"+options.title+"</span></td>"
									  +"<td colspan='3' align='left'><span style='color:"+options.titleColor+";font-size:"+options.titleFontSize+";'>总容量：<span id='totals'>"+dataObj.total+"</span>，已使用：<span  style='color: red;'>"+dataObj.users+"</span>，使用率：<span style='color: red;'>"+dataObj.peruser+"</span></span></td></tr>");			
		} 
		
		var itemDiv="";
		var trs="";
		$.each(dataObj.disks,function (i,n){
		    var index=0;//当前个数，超过5个循环取
			var percentage = (n.value*1).toFixed(2);//取后两位百分比
			if(isNaN(percentage)){
				percentage=0;
			}
			var imgWidth = parseFloat(percentage);
			if(imgWidth>0)
			{
				if(i>(options.itelTotal-1))
					index = i-(options.itelTotal-1);
				else{
					index = i;
					itemDiv="<div style='border:1px solid "+ options.itemStyle.borderColor+";background-color:"+options.itemStyle.bgColor+";font-size:"+options.itemStyle.fontSize+"'>";
					if(percentage>=options.hazard){
						itemDiv+="<div divWidth='"+imgWidth+"' style='width:0%;height:28px;background-color:"+options.itemStyle.waringBgColor+";' class='poll_plan"+index+"' >";
						itemDiv+="<div class='plan_e' style='background-color:"+options.itemStyle.waringBgColor+";'><div class='plan_c'  style='background-color:"+options.itemStyle.waringBgColor+";'></div></div>";
					}else{
						itemDiv+="<div divWidth='"+imgWidth+"' style='width:0%;height:28px;background-color:"+options.itemStyle.normalBgColor+";' class='poll_plan"+index+"' >";
						itemDiv+="<div class='plan_e' style='background-color:"+options.itemStyle.normalBgColor+";'><div class='plan_c'  style='background-color:"+options.itemStyle.normalBgColor+";'></div></div>";
					}
					itemDiv+="</div>";
					itemDiv+="</div>";
			
				}
			}
			else{
				itemDiv='';
			}
			//"<tr></tr>"
			var tds="<td width='8%' height='28px' align='right'><strong>"+n.name+"</strong></td><td width='30%' style='bgcolor:"+options.itemStyle.bgColor+";'>"+itemDiv+"</td><td width='12%'>"+n.total+"(使用率："+percentage+"%)</td>";
			if(i%2==0){
				trs+="<tr>"+tds;
			}else{
				trs+=tds+"</tr>";
			}
			
		});
		if(dataObj.disks.length%2==1){
			trs+="<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>";	
		}
		$("table",o).append(trs);
		$("div",o).each(function(i,n){
			if($(n).attr('divWidth'))
			{
				$(n).animate( { width: $(n).attr('divWidth')+'%'}, options.speed );
				$(n).removeAttr("divWidth");
			}
		});
		return this;
	};
})(jQuery);