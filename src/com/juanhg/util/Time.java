/*  -----------------------------------------------------------------
 	 @file   Time.java
     @author Juan Hernandez Garcia 
     @brief  Simple & useful class to obtain times. You can start the count,
     pause it, or stop it.
 	-----------------------------------------------------------------	
    Copyright (C) 2014  Juan Hernandez Garcia
    					
						University of Granada
	--------------------------------------------------------------------					
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package com.juanhg.util;

public class Time {
	
	long time_start, time_end;
	long final_time;
	
	
	public Time(){
		time_start = time_end = final_time = 0;
	}
	
	public void start(){
		time_start = System.currentTimeMillis();
	}
	
	public void pause(){
		this.final_time += System.currentTimeMillis() - this.time_start;
	}
	
	public void stop(){
		time_end = System.currentTimeMillis();
		this.final_time += this.time_end - this.time_start;
		time_start = time_end = final_time = 0;
	}

	public long getTime(){
		return this.final_time;
	}
}
