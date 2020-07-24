package com.leadiro.starter.service.starter.dao;

import java.lang.*;
import java.util.*;
import java.io.*;
import java.net.*;
import groovy.lang.*;
import groovy.util.*;

@org.apache.ibatis.annotations.Mapper() @org.springframework.stereotype.Repository() public interface StarterMapper
 {
;
@org.apache.ibatis.annotations.Select(value="\n        SELECT * \n        FROM starter\n        WHERE id = #{starterId}\n    ")  com.leadiro.starter.service.starter.dto.Starter getStarter(@org.apache.ibatis.annotations.Param(value="starterId") int starterId);
}
