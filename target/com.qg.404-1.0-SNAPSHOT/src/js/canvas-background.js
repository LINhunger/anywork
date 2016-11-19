	particlesJS('particles-js', {
	  particles: {
	    color: '#03e8ee',     //粒子颜色
	    shape: 'circle',   //粒子形状 : "circle", "edge" or "triangle"
	    opacity: 1,        //粒子透明度
	    size: 4,           //粒子大小
	    size_random: true, //粒子大小在随机在0到size之间
	    nb: 120,            //粒子数量

	    line_linked: {
	      enable_auto: true,  //自动连线
	      distance: 90,      //线密度
	      color: '#46f0f5',   //连线颜色
	      opacity: 1,         //连线透明度
	      width: 1,			  //连线宽度
	      condensed_mode: {
	        enable: false,
	        rotateX: 600,
	        rotateY: 600
	      }
	    },
	    anim: {
	      enable: true,
	      speed: 1
	    }
	  },
	  
	  interactivity: {  //互动性
	    enable: true,
	    mouse: {
	      distance: 250
	    },
	    detect_on: 'canvas', // "canvas" or "window"
	    mode: 'grab',
	    line_linked: {
	      opacity: .6,
	    },
	    events: {
	      onclick: {
	        enable: false,
	        mode: 'push', // "push" or "remove" (particles)
	        nb: 4
	      }
	    }
	  },
	  /* Retina Display Support : 视网膜显示支持 ?*/
	  retina_detect: true
	});