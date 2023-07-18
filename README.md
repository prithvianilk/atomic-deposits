# Atomic deposits?

- Goal: Learn about sagas
- What am I building? 
  - There are two tables, a back_accounts table and a deposits table
  - We want to build a mock system that supports a single operation:
    A debit from bank_accounts and a credit to deposits. This entire operation should be atomic, or at least should "feel" atomic
  - Ideally these tables would exist in different services and the only shared infra would be the message bus.

## Design
- Models
  - bank_accounts (user_id, amount)
  - deposits (user_id, amount)
- APIs:
  - POST /bank_accounts/debit { user_id, debit_amount } 
  - PUT /deposits { user_id, deposit_amount } 
  - POST /app/deposit { user_id, deposit_amount }
- Business logic
  1. Call /deposit API
  2. Debit via /bank_accounts/debit API, then create an outbox table
  3. A thread / process polls from this table and pushes to a kafka topic
  4. A consumer of this kafka topic consumes this event and creates a deposit