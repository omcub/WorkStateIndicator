# WorkStateIndicator
A custom JavaFX control that displays different Nodes when bound to a Work.State property.
* has default colored shapes for each Work.State property
* any or all default state Nodes can be overridden in FXML


Example Java code for binding...
```java
    . . .
    workStateIndicator.workerStateProperty().bind(someWorker.stateProperty());
    . . .
```
Example FXML...
```fxml
. . .
<?import omcub.javafx.workstateindicator.WorkStateIndicator?>
  . . .
  <WorkStateIndicator fx:id="workStateIndicator">
    <ready>
      <Rectangle
        width="42"
        height="42"
        fill="cornflowerblue"
        stroke="darkgray"
        />
   </ready>
   <running>
     <ImageView fitWidth="48"  fitHeight="48">
       <Image url="@/greenarrow.png"/>
     </ImageView>
   </running>
   <scheduled>
     <Polygon  fill="darkcyan">
       <points> <!-- diamond -->
         <Double fx:value="24.0"/> <Double fx:value="4.0"/>
         <Double fx:value="44.0"/> <Double fx:value="24.0"/>
         <Double fx:value="24.0"/> <Double fx:value="44.0"/>
         <Double fx:value="4.0"/> <Double fx:value="24.0"/>
       </points>
     </Polygon>
   </scheduled>
 </WorkStateIndicator>
 . . .
```
