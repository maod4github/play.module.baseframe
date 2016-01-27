function bBoxScrollMode1(mObj,cObj,objPar){
	var
		$bLi = mObj.children('li'),
		mCurClass = '__oc_b_box_m1_mli_cur',
		cCurClass = '__oc_b_box_m1_cli_cur',
		iBLi_w = $bLi.outerWidth(true),
		iNow = 0,
		iNumShow = 0,
		iSpeed = 300,
		iTimer = 3500,
		bAuto = true;

		mObj.width(iBLi_w*2).children('li').css('display','none').first().css('display','block').addClass(mCurClass);
		$bLi.each(function(){
			$(this).attr('number',$(this).index());
			cObj.append('<li class="__oc_b_box_m1_cli"></li>');
		});
		function mover(){
			iNumShow = $('.__oc_b_box_m1_mli_cur').attr('number');
			if(iNow>iNumShow){
				mObj.children('li').eq(iNow).css('display','block').addClass(mCurClass).siblings().removeClass(mCurClass);
				mObj.stop().animate({'left':-iBLi_w},iSpeed,function(){
					mObj.css('left',0);
					mObj.children('li').eq(iNow).siblings().css('display','none');
				});
			}
			else if(iNow<iNumShow){
				mObj.children('li').eq(iNow).css('display','block').addClass(mCurClass).siblings().removeClass(mCurClass);
				mObj.css('left',-iBLi_w).stop().animate({'left':0},iSpeed,function(){
					mObj.children('li').eq(iNow).siblings().css('display','none');
				});
			}
			else{}
		}
		cObj.children('li').first().addClass(cCurClass);
		cObj.children('li').each(function(){
			$(this).on('mouseenter mousemove',function(){
				if(mObj.is(':animated')){
					return;
				}
				iNow = $(this).index();
				$(this).addClass(cCurClass).siblings().removeClass(cCurClass);
				mover();
			});	
		});
		if(bAuto){
			var timer = setInterval(function(){
				iNow++;
				if(iNow>$bLi.length-1){
					iNow = 0;
				}
				mover();
				cObj.children('li').eq(iNow).addClass(cCurClass).siblings().removeClass(cCurClass);
			},iTimer);
			objPar.hover(function(){
				clearInterval(timer);
			},function(){
					timer = setInterval(function(){
					iNow++;
					if(iNow>$bLi.length-1){
						iNow = 0;
					}
					mover();
					cObj.children('li').eq(iNow).addClass(cCurClass).siblings().removeClass(cCurClass);
				},iTimer);
			});
		}
}


function bBoxScrollMode2($prev, $next, $wrapper, $imgList){
	var liWidth = $imgList.find("li").eq(0).width(),
	        curIndex = 0,
	        liList = $imgList.find("li");


	/*---------- 初始化banner背景 -----------*/
	/* liList.each(function(index){
		liList.eq(index).css("background","url("+liList.eq(index).attr("_src")+") center");
	}); */
	/*--------------- end -----------*/

	$next.click(function(){
		nextSlide();
	});

	function nextSlide(){
		if(!$imgList.is(":animated")){
			$imgList.stop(true,false).animate({
				"margin-left":-liWidth
			},300, function(){
				$imgList.css({"margin-left":"0"}).children().eq(0).appendTo($imgList);
			});

			if(curIndex < $imgList.find("li").length-1){
				curIndex++;
			} else {
				curIndex = 0;
			}
			return curIndex;
		}
	}
	$prev.click(function(){
		if(!$imgList.is(":animated")){
			$imgList.css({"margin-left":"0"}).children().last().prependTo($imgList);
			$imgList.stop(true,false).css("margin-left",-liWidth+"px").animate({"margin-left":0},500);

			if(curIndex > 0){
				curIndex--;
			} else {
				curIndex = $imgList.find("li").length-1;
			}
		}
	});

	var timeLock;
	function start(){
		timeLock = setInterval(function(){
       			curIndex = nextSlide(curIndex);
	    	},3000);
	}
	start();
	$wrapper.hover(
		function(){
			clearInterval(timeLock);
		},
		function(){
			start();
		}
	);
}
