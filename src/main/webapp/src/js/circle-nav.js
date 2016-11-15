window.onload=function(){

    var svg = document.getElementById('menu'),
        menu_wrapper = document.getElementById('menu-wrapper'),
        items = svg.querySelectorAll('.item'), 
        trigger = document.getElementById('trigger'), 
        label = trigger.querySelectorAll('#label')[0], 
        open = true, 
        angle = 47;

    (function(){
        var tl = new TimelineLite();
        for (var i = 0; i < items.length; i++) {
            if (window.CP.shouldStopExecution(2)) {
                break;
            }
            tl.to(items[i], 0, {
                rotation: 0,
                ease: Circ.easeOut
            }, 0);
        }
        window.CP.exitedLoop(2);
        tl.to(items, 0, {
            scale: 0,
            ease: Back.easeIn
        }, 0);
        label.innerHTML = '+';
        svg.style.pointerEvents = 'none';
        open=false;
    })();

    trigger.addEventListener('click', toggleMenu, false);
     
    function toggleMenu(event) {
        $(items).show()
        if (!event)
            var event = window.event;
        event.stopPropagation();
        open = !open;
        if (open) {
            var tl = new TimelineLite();
            tl.to(items, 0.2, {
                scale: 1,
                ease: Back.easeOut.config(4)
            }, 0.05);
            for (var i = 0; i < items.length; i++) {
                if (window.CP.shouldStopExecution(1)) {
                    break;
                }
                tl.to(items[i], 1.3, {
                    rotation: -i * angle + 'deg',
                    ease: Bounce.easeOut
                }, 0.);
            }
            window.CP.exitedLoop(1);
            label.innerHTML = '-';
            svg.style.pointerEvents = 'auto';
        } else {
            var tl = new TimelineLite();
            for (var i = 0; i < items.length; i++) {
                if (window.CP.shouldStopExecution(2)) {
                    break;
                }
                tl.to(items[i], 0.3, {
                    rotation: 0,
                    ease: Circ.easeOut
                }, 0.05);
            }
            window.CP.exitedLoop(2);
            tl.to(items, 0.3, {
                scale: 0,
                ease: Back.easeIn
            }, 0.3);
            label.innerHTML = '+';
            svg.style.pointerEvents = 'none';
        }
    }
    svg.onclick = function (e) {
        e.stopPropagation();
    };
    document.onclick = function () {
        closeMenu();
        $('#frame').hide();
    };

    function closeMenu(){
        open = false;
        var tl = new TimelineLite();
        for (var i = 0; i < items.length; i++) {
            if (window.CP.shouldStopExecution(3)) {
                break;
            }
            tl.to(items[i], 0.3, {
                rotation: 0,
                ease: Circ.easeOut
            }, 0.05);
        }
        window.CP.exitedLoop(3);
        tl.to(items, 0.3, {
            scale: 0,
            ease: Back.easeIn
        }, 0.3);
        label.innerHTML = '+';
        svg.style.pointerEvents = 'none';
    }

    $('#itemsContainer').click(function(e){
        $('#frame').show();
        var tg = e.target;
        if(tg.nodeName.toUpperCase()!='A'){
            tg = $(tg).parent();
        }
        if(tg.attr('id')==='item-1'){
            frame.$data.frame = 'myInfo';
            return false;
        }
        if(tg.attr('id')==='item-2'){
            my_alert('该功能尚未完成!');
            return false;
        }
        if(tg.attr('id')==='item-3'){
           frame.$data.frame = 'search';
            return false;
        }
        if(tg.attr('id')==='item-4'){
            frame.$data.frame = 'createTeam';
            return false;
        }
    })
}