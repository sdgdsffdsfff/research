<%@ tag pageEncoding="UTF-8"%>
<%@ attribute name="page" required="true" type="com.fpx.ce.common.page.SimplePage" description="SimplePage.java" %>
<%@ attribute name="pageSizeSelectList" type="java.lang.Number[]" required="false"  %>
<%@ attribute name="isShowPageSizeList" type="java.lang.Boolean" required="false"  %>
<%@ attribute name="formId" type="java.lang.String" required="true"  %>
<%@ attribute name="asyncMethod" type="java.lang.String" required="false"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set value="${PAGE}" var="page" />
<table class="table">
<tbody><tr>
    <td height="35">　共有 <em>${page.totalCount }</em> 条记录，当前第 <em>${page.pageNo }</em> 页，共 <em>${page.totalPage }</em> 页</td>
    <td align="right">
        <c:if test="${!page.hasPrev()}">
	        <a href="javascript:;" class="fy">首页</a>  |  
	        <a href="javascript:;" class="fy">上一页</a>  |  
        </c:if>
        <c:if test="${page.hasPrev()}">
	        <a onclick="pageTrun('${formId}','1')" href="javascript:;" class="fy2">首页</a>  |  
	        <a onclick="pageTrun('${formId}','${page.pageNo - 1}')" href="javascript:;" class="fy2">上一页</a>  |  
        </c:if>
        <c:if test="${!page.hasNext()}">
	        <a href="javascript:;" class="fy">下一页</a>  |  
            <a href="javascript:;" class="fy">尾页</a>
        </c:if>
        <c:if test="${page.hasNext()}">
	        <a onclick="pageTrun('${formId}','${page.pageNo + 1}')" href="javascript:;" class="fy2">下一页</a>  |  
	        <a onclick="pageTrun('${formId}','${page.totalPage}')" href="javascript:;" class="fy2">尾页</a>
        </c:if>
    </td>
  </tr>
</tbody>
</table>