package forms

/**
  * Form containing data to create an item.
  * @param id id of the item.
  * @param categoryID category id of the item.
  * @param name name of the item.
  * @param price price of item.
  */
case class EditItemForm(id: Long, categoryID: Long, name: String, price: Int, visibility: Option[Boolean])