# Atomic deposits?

- Goal: Learn about sagas
- What am I building?
    - There are two services, a back_accounts and deposits
    - We want to build a mock system that supports a single operation:
      A debit from bank_accounts' table and a credit to deposits' table. This entire operation should be atomic, or at
      least should "feel" atomic.
    - Ideally these tables would exist in different services and the only shared infra would be the message bus.
    - A deposit creation would be triggered via a gateway API on the bank_accounts service that would orchestrate this
      transaction. The bank_accounts service would debit money from the account and publish a deposit event.
    - The above operation should be done via the outbox pattern.

## Design

- Models
    - bank_accounts (user_id, amount)
    - deposits (user_id, amount)
- APIs:
    - POST /bank_accounts/user_id { debit_amount }
    - PUT /deposits/user_id { deposit_amount }
    - POST /bank_accounts/deposit/user_id { deposit_amount }
- Business logic
    1. Create bank_account via /bank_accounts/user_id API
    2. Debit via /bank_accounts/deposit API and create an event on outbox table?
    3. A thread / process polls from this table and pushes to a kafka topic
    4. A consumer of this kafka topic consumes this event and creates a deposit