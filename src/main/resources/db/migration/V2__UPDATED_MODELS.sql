alter table category_products
    rename to product_categories;

alter table product_categories
    rename column category_id to categories_id;