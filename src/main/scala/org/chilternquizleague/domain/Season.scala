package org.chilternquizleague.domain

import java.time.Year

case class Season(
    id:String,
    startYear:Year,
    endYear:Year,
    text:Ref[Text],
    competitions:List[Ref[Competition]],
    retired:Boolean = false
) extends Entity