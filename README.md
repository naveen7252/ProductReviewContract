# ProductReviewContract
Contract to write reviews for any product to be executed on NULS blockchain.

There are two methods in the contract:

1. Write Review: We can write review to any product by passing product id and review comments to this method. Reviews are stored in Nuls Blockchain.The account which executed this method is treated ad writer for that review.
This method is payable type, so to execute this method, small fee in NULS will be incurred.

2.Get Reviews: We can get all the reviews of a product by passing product id.This method VIEW type, so there will not be any fee for calling this method.
