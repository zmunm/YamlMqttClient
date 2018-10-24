package com.zmunm.narvcorp.sample.core.mysql.dao

import com.zmunm.narvcorp.sample.core.dto.*
import org.apache.ibatis.annotations.*

@Mapper
interface AdminDAO {
    @Insert("""
    INSERT IGNORE INTO $ADMIN$_USER
    ($ID,$PASSWORD,$NAME,$REG_DATE,$EMAIL)
    VALUES (#{$ADMIN.$ID},#{$ADMIN.$PASSWORD},#{$ADMIN.$NAME},$NOW,#{$ADMIN.$EMAIL})
	""")
	fun insert(@Param(ADMIN) admin: Admin)

	@Update("""
    UPDATE $ADMIN$_USER
    set $DEL = 1,
    $MOD_DATE = $NOW
    WHERE $ID = #{$ID}
    """)
	fun delete(@Param(ID) id:String)

	@Update("""
    UPDATE $ADMIN$_USER
    set $DEL = 0
    WHERE $ID = #{$ID}
    """)
	fun restore(@Param(ID) id:String)

	@Select("""
    SELECT
    	$ID,
    	$PASSWORD
    FROM $ADMIN$_USER
    WHERE
    	$ID = #{$ID} and
    	$DEL != 1
    """)
	fun selectPw(@Param(ID) id:String):List<Admin>

	@Select("""
    SELECT
    	$ID,
    	password,
    	$NAME,
    	email
    FROM $ADMIN$_USER
    WHERE
    	$ID = #{$ID} and
    	$DEL != 1
    """)
	fun selectOne(@Param(ID) id:String):List<Admin>
}
