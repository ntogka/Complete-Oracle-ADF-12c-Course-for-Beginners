package view.bean;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.binding.DCIteratorBinding;

import oracle.jbo.Row;

import oracle.jbo.ViewObject;

import org.apache.myfaces.trinidad.event.DisclosureEvent;

import org.apache.myfaces.trinidad.event.SelectionEvent;

import view.MyADFUtil;

public class DeptUseTemplateBean
{
  public DeptUseTemplateBean()
  {
  }

  public String navigateToEmpsAction()
  {
    System.out.println("Called to navigate to emp....");
    
    Object departmentId = MyADFUtil.getAttributeFromIterator("DepartmentsView1Iterator", "DepartmentId");
    System.out.println("The current Dept is: " + departmentId);
    
    if(departmentId.toString().equals("70"))
    {
      MyADFUtil.showErrorMessage("Sorry, you can't navigate to employees for dept 70");
      return null;
    }
    
    return "empTemp";
  }

  public void deptFormDisclosureListener(DisclosureEvent disclosureEvent)
  {
    if(disclosureEvent.isExpanded())
    {
      System.out.println("I am comming..");
    }
    else
    {
      System.out.println("See you. Bye..");
    }
  }

  public void overrideTableSelectionListener(SelectionEvent selectionEvent)
  {
    MyADFUtil.makeTableSelectedRowCurrentRow("#{bindings.DepartmentsView1.collectionModel.makeCurrent}", selectionEvent);
    System.out.println("After selected rowwww");
  }

  public void deptNAmeValueChangeListener(ValueChangeEvent valueChangeEvent)
  {
    System.out.println("The new dept name is: " + valueChangeEvent.getNewValue());
  }


  public void createNewDept(ActionEvent actionEvent)
  {
    MyADFUtil.executeOperation("CreateInsert");
    MyADFUtil.setAttributeInIterator("DepartmentsView1Iterator", "DepartmentName", "Dept Name from Backbean");    
  }

  public void filterDeptVOActionListener(ActionEvent actionEvent)
  {
    ViewObject deptVO = MyADFUtil.getIterator("DepartmentsView1Iterator").getViewObject();
    deptVO.setWhereClause("DEPARTMENT_ID = 80");
    deptVO.executeQuery();
  }

  public void resetDeptVOActionListener(ActionEvent actionEvent)
  {
    ViewObject deptVO = MyADFUtil.getIterator("DepartmentsView1Iterator").getViewObject();
    deptVO.setWhereClause(null);
    deptVO.executeQuery();
  }

  public void loopOverDeptVOActionListener(ActionEvent actionEvent)
  {
    DCIteratorBinding deptIter = MyADFUtil.getIterator("DepartmentsView1Iterator");
    for (int i = 0; i < deptIter.getEstimatedRowCount(); i++)
    {
      Row r = deptIter.getRowAtRangeIndex(i);
      System.out.println("dept id: " + r.getAttribute("DepartmentId") + " - dept name: " + r.getAttribute("DepartmentName"));
      
      if(r.getAttribute("DepartmentId").toString().equals("100"))
      {
        r.setAttribute("DepartmentName", "Finance100");
      }
    }
  }

  public void addValueINSessionScope(ActionEvent actionEvent)
  {
    MyADFUtil.putInSessionScope("deptName", "My Own Development department");
  }
}
