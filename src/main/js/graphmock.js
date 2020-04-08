import React, { Component } from 'react';
import {XYPlot, VerticalBarSeries, LabelSeries} from 'react-vis';

class GraphDataMock extends Component {
  render() {
	  const data = this.convertWeeksToData( this.props.weeks );
      const chartWidth = 300;
      const chartHeight = 300;
      const chartDomain = [0, chartHeight];
      return (
    	<div>
          <XYPlot 
             xType="ordinal" 
             width={chartWidth} 
             height={chartHeight} 
             yDomain={chartDomain}
           >
              <VerticalBarSeries data={data} />
              <LabelSeries
              	data={data}
              	labelAnchorX="middle"
              	labelAnchorY="text-after-edge"
              />          
           </XYPlot>
        </div>
      );
  }
  
  convertWeeksToData( weeks ) {
	  var toReturn = [];
	  var newWeekPoint;
	  for ( var i = 0; i < weeks.length; i++ ){
		  newWeekPoint = {};
		  newWeekPoint.x = weeks[ i ].dateRecorded;
		  newWeekPoint.y = weeks[ i ].totalMiles;
		  newWeekPoint.label = weeks[ i ].totalMiles;
		  toReturn.push( newWeekPoint );
	  }
	  
	  console.log( toReturn );
	  return toReturn;
  }
}

export default GraphDataMock;
