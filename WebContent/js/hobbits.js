function DateSelector(selYear, selMonth, selDay)
{
    this.selYear = selYear;
    this.selMonth = selMonth;
    this.selDay = selDay;
    this.InitYearSelect();
    this.InitMonthSelect();
    this.InitDaySelect();
    this.selYear.Group = this;
    this.selMonth.Group = this;
    
    // ¸øÄê·Ý¡¢ÔÂ·ÝÏÂÀ­²Ëµ¥Ìí¼Ó´¦ÀíonchangeÊÂ¼þµÄº¯Êý
    if(window.document.all != null) // IE
    {
        this.selYear.attachEvent("onchange", DateSelector.Onchange);
        this.selMonth.attachEvent("onchange", DateSelector.Onchange);
    }
    else // Firefox
    {
        this.selYear.addEventListener("change", DateSelector.Onchange, false);
        this.selMonth.addEventListener("change", DateSelector.Onchange, false);
    }
}

DateSelector.prototype.MinYear = 2015;

DateSelector.prototype.MaxYear = (new Date()).getFullYear();

DateSelector.prototype.InitYearSelect = function()
{
    for(var i = this.MaxYear; i >= this.MinYear; i--)
    {
        var op = window.document.createElement("OPTION");
        op.value = i;
        op.innerHTML = i;
        this.selYear.appendChild(op);
    }
}
DateSelector.prototype.InitMonthSelect = function()
{
    for(var i = 1; i < 13; i++)
    {
        var op = window.document.createElement("OPTION");
        op.value = i;
        op.innerHTML = i;
        this.selMonth.appendChild(op);
    }
}
DateSelector.DaysInMonth = function(year, month)
{
    var date = new Date(year, month, 0);
    return date.getDate();
}
DateSelector.prototype.InitDaySelect = function()
{
    // Ê¹ÓÃparseIntº¯Êý»ñÈ¡µ±Ç°µÄÄê·ÝºÍÔÂ·Ý
    var year = parseInt(this.selYear.value);
    var month = parseInt(this.selMonth.value);
    
    // »ñÈ¡µ±ÔÂµÄÌìÊý
    var daysInMonth = DateSelector.DaysInMonth(year, month);
    
    // Çå¿ÕÔ­ÓÐµÄÑ¡Ïî
    this.selDay.options.length = 0;
    // Ñ­»·Ìí¼ÓOPIONÔªËØµ½ÌìÊýselect¶ÔÏóÖÐ
    for(var i = 1; i <= daysInMonth ; i++)
    {
        // ÐÂ½¨Ò»¸öOPTION¶ÔÏó
        var op = window.document.createElement("OPTION");
        
        // ÉèÖÃOPTION¶ÔÏóµÄÖµ
        op.value = i;
        
        // ÉèÖÃOPTION¶ÔÏóµÄÄÚÈÝ
        op.innerHTML = i;
        
        // Ìí¼Óµ½ÌìÊýselect¶ÔÏó
        this.selDay.appendChild(op);
    }
}
// ´¦ÀíÄê·ÝºÍÔÂ·ÝonchangeÊÂ¼þµÄ·½·¨£¬Ëü»ñÈ¡ÊÂ¼þÀ´Ô´¶ÔÏó£¨¼´selYear»òselMonth£©
// ²¢µ÷ÓÃËüµÄGroup¶ÔÏó£¨¼´DateSelectorÊµÀý£¬Çë¼û¹¹Ôìº¯Êý£©Ìá¹©µÄInitDaySelect·½·¨ÖØÐÂ³õÊ¼»¯ÌìÊý
// ²ÎÊýeÎªevent¶ÔÏó
DateSelector.Onchange = function(e)
{
    var selector = window.document.all != null ? e.srcElement : e.target;
    selector.Group.InitDaySelect();
}

