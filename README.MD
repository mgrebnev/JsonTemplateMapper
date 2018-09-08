# JsonTemplateMapper

**JsonTemplateMapper** - Lib for simple mapping POJO to json through json template

### Example
Simple POJO 
![POJO](images/screen_1.png)<br /><br />
It is necessary to obtain JSON from **User** entity of the following form
![tree_json](images/screen_2.png)<br /><br />
JsonTemplateMapper create json data through preformed json template. For example, let's create a template(**user.json**) adapted for **User** entity:
![user_json_template](images/screen_3.png)<br /><br />
In $(...) expression put target POJO class members names. The following example shows the completion of the json template:
![code_example](images/screen_5.png)<br /><br />
Result output:
![output_json](images/screen_4.png)<br /><br />

### Integration with Spring framework

Create TemplateMapper bean:
![template_mapper_bean](images/screen_6.png)<br /><br />
Use mapper in rest controller:
![template_mapper_bean_in_controller](images/screen_7.png)
    