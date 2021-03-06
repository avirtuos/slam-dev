// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: protobuf/lidar.proto

#define INTERNAL_SUPPRESS_PROTOBUF_FIELD_DEPRECATION
#include "protobuf/lidar.pb.h"

#include <algorithm>

#include <google/protobuf/stubs/common.h>
#include <google/protobuf/stubs/port.h>
#include <google/protobuf/stubs/once.h>
#include <google/protobuf/io/coded_stream.h>
#include <google/protobuf/wire_format_lite_inl.h>
#include <google/protobuf/descriptor.h>
#include <google/protobuf/generated_message_reflection.h>
#include <google/protobuf/reflection_ops.h>
#include <google/protobuf/wire_format.h>
// @@protoc_insertion_point(includes)

namespace {

const ::google::protobuf::Descriptor* Greeting_descriptor_ = NULL;
const ::google::protobuf::internal::GeneratedMessageReflection*
  Greeting_reflection_ = NULL;
const ::google::protobuf::Descriptor* Point_descriptor_ = NULL;
const ::google::protobuf::internal::GeneratedMessageReflection*
  Point_reflection_ = NULL;

}  // namespace


void protobuf_AssignDesc_protobuf_2flidar_2eproto() GOOGLE_ATTRIBUTE_COLD;
void protobuf_AssignDesc_protobuf_2flidar_2eproto() {
  protobuf_AddDesc_protobuf_2flidar_2eproto();
  const ::google::protobuf::FileDescriptor* file =
    ::google::protobuf::DescriptorPool::generated_pool()->FindFileByName(
      "protobuf/lidar.proto");
  GOOGLE_CHECK(file != NULL);
  Greeting_descriptor_ = file->message_type(0);
  static const int Greeting_offsets_[1] = {
    GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(Greeting, greeting_),
  };
  Greeting_reflection_ =
    ::google::protobuf::internal::GeneratedMessageReflection::NewGeneratedMessageReflection(
      Greeting_descriptor_,
      Greeting::default_instance_,
      Greeting_offsets_,
      GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(Greeting, _has_bits_[0]),
      -1,
      -1,
      sizeof(Greeting),
      GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(Greeting, _internal_metadata_),
      -1);
  Point_descriptor_ = file->message_type(1);
  static const int Point_offsets_[5] = {
    GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(Point, x_),
    GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(Point, y_),
    GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(Point, distance_),
    GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(Point, angle_),
    GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(Point, end_),
  };
  Point_reflection_ =
    ::google::protobuf::internal::GeneratedMessageReflection::NewGeneratedMessageReflection(
      Point_descriptor_,
      Point::default_instance_,
      Point_offsets_,
      GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(Point, _has_bits_[0]),
      -1,
      -1,
      sizeof(Point),
      GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(Point, _internal_metadata_),
      -1);
}

namespace {

GOOGLE_PROTOBUF_DECLARE_ONCE(protobuf_AssignDescriptors_once_);
inline void protobuf_AssignDescriptorsOnce() {
  ::google::protobuf::GoogleOnceInit(&protobuf_AssignDescriptors_once_,
                 &protobuf_AssignDesc_protobuf_2flidar_2eproto);
}

void protobuf_RegisterTypes(const ::std::string&) GOOGLE_ATTRIBUTE_COLD;
void protobuf_RegisterTypes(const ::std::string&) {
  protobuf_AssignDescriptorsOnce();
  ::google::protobuf::MessageFactory::InternalRegisterGeneratedMessage(
      Greeting_descriptor_, &Greeting::default_instance());
  ::google::protobuf::MessageFactory::InternalRegisterGeneratedMessage(
      Point_descriptor_, &Point::default_instance());
}

}  // namespace

void protobuf_ShutdownFile_protobuf_2flidar_2eproto() {
  delete Greeting::default_instance_;
  delete Greeting_reflection_;
  delete Point::default_instance_;
  delete Point_reflection_;
}

void protobuf_AddDesc_protobuf_2flidar_2eproto() GOOGLE_ATTRIBUTE_COLD;
void protobuf_AddDesc_protobuf_2flidar_2eproto() {
  static bool already_here = false;
  if (already_here) return;
  already_here = true;
  GOOGLE_PROTOBUF_VERIFY_VERSION;

  ::google::protobuf::DescriptorPool::InternalAddGeneratedFile(
    "\n\024protobuf/lidar.proto\"\034\n\010Greeting\022\020\n\010gr"
    "eeting\030\001 \002(\t\"K\n\005Point\022\t\n\001x\030\001 \002(\005\022\t\n\001y\030\002 "
    "\002(\005\022\020\n\010distance\030\003 \002(\002\022\r\n\005angle\030\004 \002(\002\022\013\n\003"
    "end\030\005 \002(\010B\031\n\021org.virtuoso.slamB\004Slam", 156);
  ::google::protobuf::MessageFactory::InternalRegisterGeneratedFile(
    "protobuf/lidar.proto", &protobuf_RegisterTypes);
  Greeting::default_instance_ = new Greeting();
  Point::default_instance_ = new Point();
  Greeting::default_instance_->InitAsDefaultInstance();
  Point::default_instance_->InitAsDefaultInstance();
  ::google::protobuf::internal::OnShutdown(&protobuf_ShutdownFile_protobuf_2flidar_2eproto);
}

// Force AddDescriptors() to be called at static initialization time.
struct StaticDescriptorInitializer_protobuf_2flidar_2eproto {
  StaticDescriptorInitializer_protobuf_2flidar_2eproto() {
    protobuf_AddDesc_protobuf_2flidar_2eproto();
  }
} static_descriptor_initializer_protobuf_2flidar_2eproto_;

// ===================================================================

#if !defined(_MSC_VER) || _MSC_VER >= 1900
const int Greeting::kGreetingFieldNumber;
#endif  // !defined(_MSC_VER) || _MSC_VER >= 1900

Greeting::Greeting()
  : ::google::protobuf::Message(), _internal_metadata_(NULL) {
  SharedCtor();
  // @@protoc_insertion_point(constructor:Greeting)
}

void Greeting::InitAsDefaultInstance() {
}

Greeting::Greeting(const Greeting& from)
  : ::google::protobuf::Message(),
    _internal_metadata_(NULL) {
  SharedCtor();
  MergeFrom(from);
  // @@protoc_insertion_point(copy_constructor:Greeting)
}

void Greeting::SharedCtor() {
  ::google::protobuf::internal::GetEmptyString();
  _cached_size_ = 0;
  greeting_.UnsafeSetDefault(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
  ::memset(_has_bits_, 0, sizeof(_has_bits_));
}

Greeting::~Greeting() {
  // @@protoc_insertion_point(destructor:Greeting)
  SharedDtor();
}

void Greeting::SharedDtor() {
  greeting_.DestroyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
  if (this != default_instance_) {
  }
}

void Greeting::SetCachedSize(int size) const {
  GOOGLE_SAFE_CONCURRENT_WRITES_BEGIN();
  _cached_size_ = size;
  GOOGLE_SAFE_CONCURRENT_WRITES_END();
}
const ::google::protobuf::Descriptor* Greeting::descriptor() {
  protobuf_AssignDescriptorsOnce();
  return Greeting_descriptor_;
}

const Greeting& Greeting::default_instance() {
  if (default_instance_ == NULL) protobuf_AddDesc_protobuf_2flidar_2eproto();
  return *default_instance_;
}

Greeting* Greeting::default_instance_ = NULL;

Greeting* Greeting::New(::google::protobuf::Arena* arena) const {
  Greeting* n = new Greeting;
  if (arena != NULL) {
    arena->Own(n);
  }
  return n;
}

void Greeting::Clear() {
// @@protoc_insertion_point(message_clear_start:Greeting)
  if (has_greeting()) {
    greeting_.ClearToEmptyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
  }
  ::memset(_has_bits_, 0, sizeof(_has_bits_));
  if (_internal_metadata_.have_unknown_fields()) {
    mutable_unknown_fields()->Clear();
  }
}

bool Greeting::MergePartialFromCodedStream(
    ::google::protobuf::io::CodedInputStream* input) {
#define DO_(EXPRESSION) if (!GOOGLE_PREDICT_TRUE(EXPRESSION)) goto failure
  ::google::protobuf::uint32 tag;
  // @@protoc_insertion_point(parse_start:Greeting)
  for (;;) {
    ::std::pair< ::google::protobuf::uint32, bool> p = input->ReadTagWithCutoff(127);
    tag = p.first;
    if (!p.second) goto handle_unusual;
    switch (::google::protobuf::internal::WireFormatLite::GetTagFieldNumber(tag)) {
      // required string greeting = 1;
      case 1: {
        if (tag == 10) {
          DO_(::google::protobuf::internal::WireFormatLite::ReadString(
                input, this->mutable_greeting()));
          ::google::protobuf::internal::WireFormat::VerifyUTF8StringNamedField(
            this->greeting().data(), this->greeting().length(),
            ::google::protobuf::internal::WireFormat::PARSE,
            "Greeting.greeting");
        } else {
          goto handle_unusual;
        }
        if (input->ExpectAtEnd()) goto success;
        break;
      }

      default: {
      handle_unusual:
        if (tag == 0 ||
            ::google::protobuf::internal::WireFormatLite::GetTagWireType(tag) ==
            ::google::protobuf::internal::WireFormatLite::WIRETYPE_END_GROUP) {
          goto success;
        }
        DO_(::google::protobuf::internal::WireFormat::SkipField(
              input, tag, mutable_unknown_fields()));
        break;
      }
    }
  }
success:
  // @@protoc_insertion_point(parse_success:Greeting)
  return true;
failure:
  // @@protoc_insertion_point(parse_failure:Greeting)
  return false;
#undef DO_
}

void Greeting::SerializeWithCachedSizes(
    ::google::protobuf::io::CodedOutputStream* output) const {
  // @@protoc_insertion_point(serialize_start:Greeting)
  // required string greeting = 1;
  if (has_greeting()) {
    ::google::protobuf::internal::WireFormat::VerifyUTF8StringNamedField(
      this->greeting().data(), this->greeting().length(),
      ::google::protobuf::internal::WireFormat::SERIALIZE,
      "Greeting.greeting");
    ::google::protobuf::internal::WireFormatLite::WriteStringMaybeAliased(
      1, this->greeting(), output);
  }

  if (_internal_metadata_.have_unknown_fields()) {
    ::google::protobuf::internal::WireFormat::SerializeUnknownFields(
        unknown_fields(), output);
  }
  // @@protoc_insertion_point(serialize_end:Greeting)
}

::google::protobuf::uint8* Greeting::InternalSerializeWithCachedSizesToArray(
    bool deterministic, ::google::protobuf::uint8* target) const {
  // @@protoc_insertion_point(serialize_to_array_start:Greeting)
  // required string greeting = 1;
  if (has_greeting()) {
    ::google::protobuf::internal::WireFormat::VerifyUTF8StringNamedField(
      this->greeting().data(), this->greeting().length(),
      ::google::protobuf::internal::WireFormat::SERIALIZE,
      "Greeting.greeting");
    target =
      ::google::protobuf::internal::WireFormatLite::WriteStringToArray(
        1, this->greeting(), target);
  }

  if (_internal_metadata_.have_unknown_fields()) {
    target = ::google::protobuf::internal::WireFormat::SerializeUnknownFieldsToArray(
        unknown_fields(), target);
  }
  // @@protoc_insertion_point(serialize_to_array_end:Greeting)
  return target;
}

int Greeting::ByteSize() const {
// @@protoc_insertion_point(message_byte_size_start:Greeting)
  int total_size = 0;

  // required string greeting = 1;
  if (has_greeting()) {
    total_size += 1 +
      ::google::protobuf::internal::WireFormatLite::StringSize(
        this->greeting());
  }
  if (_internal_metadata_.have_unknown_fields()) {
    total_size +=
      ::google::protobuf::internal::WireFormat::ComputeUnknownFieldsSize(
        unknown_fields());
  }
  GOOGLE_SAFE_CONCURRENT_WRITES_BEGIN();
  _cached_size_ = total_size;
  GOOGLE_SAFE_CONCURRENT_WRITES_END();
  return total_size;
}

void Greeting::MergeFrom(const ::google::protobuf::Message& from) {
// @@protoc_insertion_point(generalized_merge_from_start:Greeting)
  if (GOOGLE_PREDICT_FALSE(&from == this)) {
    ::google::protobuf::internal::MergeFromFail(__FILE__, __LINE__);
  }
  const Greeting* source = 
      ::google::protobuf::internal::DynamicCastToGenerated<const Greeting>(
          &from);
  if (source == NULL) {
  // @@protoc_insertion_point(generalized_merge_from_cast_fail:Greeting)
    ::google::protobuf::internal::ReflectionOps::Merge(from, this);
  } else {
  // @@protoc_insertion_point(generalized_merge_from_cast_success:Greeting)
    MergeFrom(*source);
  }
}

void Greeting::MergeFrom(const Greeting& from) {
// @@protoc_insertion_point(class_specific_merge_from_start:Greeting)
  if (GOOGLE_PREDICT_FALSE(&from == this)) {
    ::google::protobuf::internal::MergeFromFail(__FILE__, __LINE__);
  }
  if (from._has_bits_[0 / 32] & (0xffu << (0 % 32))) {
    if (from.has_greeting()) {
      set_has_greeting();
      greeting_.AssignWithDefault(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), from.greeting_);
    }
  }
  if (from._internal_metadata_.have_unknown_fields()) {
    mutable_unknown_fields()->MergeFrom(from.unknown_fields());
  }
}

void Greeting::CopyFrom(const ::google::protobuf::Message& from) {
// @@protoc_insertion_point(generalized_copy_from_start:Greeting)
  if (&from == this) return;
  Clear();
  MergeFrom(from);
}

void Greeting::CopyFrom(const Greeting& from) {
// @@protoc_insertion_point(class_specific_copy_from_start:Greeting)
  if (&from == this) return;
  Clear();
  MergeFrom(from);
}

bool Greeting::IsInitialized() const {
  if ((_has_bits_[0] & 0x00000001) != 0x00000001) return false;

  return true;
}

void Greeting::Swap(Greeting* other) {
  if (other == this) return;
  InternalSwap(other);
}
void Greeting::InternalSwap(Greeting* other) {
  greeting_.Swap(&other->greeting_);
  std::swap(_has_bits_[0], other->_has_bits_[0]);
  _internal_metadata_.Swap(&other->_internal_metadata_);
  std::swap(_cached_size_, other->_cached_size_);
}

::google::protobuf::Metadata Greeting::GetMetadata() const {
  protobuf_AssignDescriptorsOnce();
  ::google::protobuf::Metadata metadata;
  metadata.descriptor = Greeting_descriptor_;
  metadata.reflection = Greeting_reflection_;
  return metadata;
}

#if PROTOBUF_INLINE_NOT_IN_HEADERS
// Greeting

// required string greeting = 1;
bool Greeting::has_greeting() const {
  return (_has_bits_[0] & 0x00000001u) != 0;
}
void Greeting::set_has_greeting() {
  _has_bits_[0] |= 0x00000001u;
}
void Greeting::clear_has_greeting() {
  _has_bits_[0] &= ~0x00000001u;
}
void Greeting::clear_greeting() {
  greeting_.ClearToEmptyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
  clear_has_greeting();
}
 const ::std::string& Greeting::greeting() const {
  // @@protoc_insertion_point(field_get:Greeting.greeting)
  return greeting_.GetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
 void Greeting::set_greeting(const ::std::string& value) {
  set_has_greeting();
  greeting_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), value);
  // @@protoc_insertion_point(field_set:Greeting.greeting)
}
 void Greeting::set_greeting(const char* value) {
  set_has_greeting();
  greeting_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), ::std::string(value));
  // @@protoc_insertion_point(field_set_char:Greeting.greeting)
}
 void Greeting::set_greeting(const char* value, size_t size) {
  set_has_greeting();
  greeting_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(),
      ::std::string(reinterpret_cast<const char*>(value), size));
  // @@protoc_insertion_point(field_set_pointer:Greeting.greeting)
}
 ::std::string* Greeting::mutable_greeting() {
  set_has_greeting();
  // @@protoc_insertion_point(field_mutable:Greeting.greeting)
  return greeting_.MutableNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
 ::std::string* Greeting::release_greeting() {
  // @@protoc_insertion_point(field_release:Greeting.greeting)
  clear_has_greeting();
  return greeting_.ReleaseNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
 void Greeting::set_allocated_greeting(::std::string* greeting) {
  if (greeting != NULL) {
    set_has_greeting();
  } else {
    clear_has_greeting();
  }
  greeting_.SetAllocatedNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), greeting);
  // @@protoc_insertion_point(field_set_allocated:Greeting.greeting)
}

#endif  // PROTOBUF_INLINE_NOT_IN_HEADERS

// ===================================================================

#if !defined(_MSC_VER) || _MSC_VER >= 1900
const int Point::kXFieldNumber;
const int Point::kYFieldNumber;
const int Point::kDistanceFieldNumber;
const int Point::kAngleFieldNumber;
const int Point::kEndFieldNumber;
#endif  // !defined(_MSC_VER) || _MSC_VER >= 1900

Point::Point()
  : ::google::protobuf::Message(), _internal_metadata_(NULL) {
  SharedCtor();
  // @@protoc_insertion_point(constructor:Point)
}

void Point::InitAsDefaultInstance() {
}

Point::Point(const Point& from)
  : ::google::protobuf::Message(),
    _internal_metadata_(NULL) {
  SharedCtor();
  MergeFrom(from);
  // @@protoc_insertion_point(copy_constructor:Point)
}

void Point::SharedCtor() {
  _cached_size_ = 0;
  x_ = 0;
  y_ = 0;
  distance_ = 0;
  angle_ = 0;
  end_ = false;
  ::memset(_has_bits_, 0, sizeof(_has_bits_));
}

Point::~Point() {
  // @@protoc_insertion_point(destructor:Point)
  SharedDtor();
}

void Point::SharedDtor() {
  if (this != default_instance_) {
  }
}

void Point::SetCachedSize(int size) const {
  GOOGLE_SAFE_CONCURRENT_WRITES_BEGIN();
  _cached_size_ = size;
  GOOGLE_SAFE_CONCURRENT_WRITES_END();
}
const ::google::protobuf::Descriptor* Point::descriptor() {
  protobuf_AssignDescriptorsOnce();
  return Point_descriptor_;
}

const Point& Point::default_instance() {
  if (default_instance_ == NULL) protobuf_AddDesc_protobuf_2flidar_2eproto();
  return *default_instance_;
}

Point* Point::default_instance_ = NULL;

Point* Point::New(::google::protobuf::Arena* arena) const {
  Point* n = new Point;
  if (arena != NULL) {
    arena->Own(n);
  }
  return n;
}

void Point::Clear() {
// @@protoc_insertion_point(message_clear_start:Point)
#if defined(__clang__)
#define ZR_HELPER_(f) \
  _Pragma("clang diagnostic push") \
  _Pragma("clang diagnostic ignored \"-Winvalid-offsetof\"") \
  __builtin_offsetof(Point, f) \
  _Pragma("clang diagnostic pop")
#else
#define ZR_HELPER_(f) reinterpret_cast<char*>(\
  &reinterpret_cast<Point*>(16)->f)
#endif

#define ZR_(first, last) do {\
  ::memset(&first, 0,\
           ZR_HELPER_(last) - ZR_HELPER_(first) + sizeof(last));\
} while (0)

  if (_has_bits_[0 / 32] & 31u) {
    ZR_(x_, end_);
  }

#undef ZR_HELPER_
#undef ZR_

  ::memset(_has_bits_, 0, sizeof(_has_bits_));
  if (_internal_metadata_.have_unknown_fields()) {
    mutable_unknown_fields()->Clear();
  }
}

bool Point::MergePartialFromCodedStream(
    ::google::protobuf::io::CodedInputStream* input) {
#define DO_(EXPRESSION) if (!GOOGLE_PREDICT_TRUE(EXPRESSION)) goto failure
  ::google::protobuf::uint32 tag;
  // @@protoc_insertion_point(parse_start:Point)
  for (;;) {
    ::std::pair< ::google::protobuf::uint32, bool> p = input->ReadTagWithCutoff(127);
    tag = p.first;
    if (!p.second) goto handle_unusual;
    switch (::google::protobuf::internal::WireFormatLite::GetTagFieldNumber(tag)) {
      // required int32 x = 1;
      case 1: {
        if (tag == 8) {
          DO_((::google::protobuf::internal::WireFormatLite::ReadPrimitive<
                   ::google::protobuf::int32, ::google::protobuf::internal::WireFormatLite::TYPE_INT32>(
                 input, &x_)));
          set_has_x();
        } else {
          goto handle_unusual;
        }
        if (input->ExpectTag(16)) goto parse_y;
        break;
      }

      // required int32 y = 2;
      case 2: {
        if (tag == 16) {
         parse_y:
          DO_((::google::protobuf::internal::WireFormatLite::ReadPrimitive<
                   ::google::protobuf::int32, ::google::protobuf::internal::WireFormatLite::TYPE_INT32>(
                 input, &y_)));
          set_has_y();
        } else {
          goto handle_unusual;
        }
        if (input->ExpectTag(29)) goto parse_distance;
        break;
      }

      // required float distance = 3;
      case 3: {
        if (tag == 29) {
         parse_distance:
          DO_((::google::protobuf::internal::WireFormatLite::ReadPrimitive<
                   float, ::google::protobuf::internal::WireFormatLite::TYPE_FLOAT>(
                 input, &distance_)));
          set_has_distance();
        } else {
          goto handle_unusual;
        }
        if (input->ExpectTag(37)) goto parse_angle;
        break;
      }

      // required float angle = 4;
      case 4: {
        if (tag == 37) {
         parse_angle:
          DO_((::google::protobuf::internal::WireFormatLite::ReadPrimitive<
                   float, ::google::protobuf::internal::WireFormatLite::TYPE_FLOAT>(
                 input, &angle_)));
          set_has_angle();
        } else {
          goto handle_unusual;
        }
        if (input->ExpectTag(40)) goto parse_end;
        break;
      }

      // required bool end = 5;
      case 5: {
        if (tag == 40) {
         parse_end:
          DO_((::google::protobuf::internal::WireFormatLite::ReadPrimitive<
                   bool, ::google::protobuf::internal::WireFormatLite::TYPE_BOOL>(
                 input, &end_)));
          set_has_end();
        } else {
          goto handle_unusual;
        }
        if (input->ExpectAtEnd()) goto success;
        break;
      }

      default: {
      handle_unusual:
        if (tag == 0 ||
            ::google::protobuf::internal::WireFormatLite::GetTagWireType(tag) ==
            ::google::protobuf::internal::WireFormatLite::WIRETYPE_END_GROUP) {
          goto success;
        }
        DO_(::google::protobuf::internal::WireFormat::SkipField(
              input, tag, mutable_unknown_fields()));
        break;
      }
    }
  }
success:
  // @@protoc_insertion_point(parse_success:Point)
  return true;
failure:
  // @@protoc_insertion_point(parse_failure:Point)
  return false;
#undef DO_
}

void Point::SerializeWithCachedSizes(
    ::google::protobuf::io::CodedOutputStream* output) const {
  // @@protoc_insertion_point(serialize_start:Point)
  // required int32 x = 1;
  if (has_x()) {
    ::google::protobuf::internal::WireFormatLite::WriteInt32(1, this->x(), output);
  }

  // required int32 y = 2;
  if (has_y()) {
    ::google::protobuf::internal::WireFormatLite::WriteInt32(2, this->y(), output);
  }

  // required float distance = 3;
  if (has_distance()) {
    ::google::protobuf::internal::WireFormatLite::WriteFloat(3, this->distance(), output);
  }

  // required float angle = 4;
  if (has_angle()) {
    ::google::protobuf::internal::WireFormatLite::WriteFloat(4, this->angle(), output);
  }

  // required bool end = 5;
  if (has_end()) {
    ::google::protobuf::internal::WireFormatLite::WriteBool(5, this->end(), output);
  }

  if (_internal_metadata_.have_unknown_fields()) {
    ::google::protobuf::internal::WireFormat::SerializeUnknownFields(
        unknown_fields(), output);
  }
  // @@protoc_insertion_point(serialize_end:Point)
}

::google::protobuf::uint8* Point::InternalSerializeWithCachedSizesToArray(
    bool deterministic, ::google::protobuf::uint8* target) const {
  // @@protoc_insertion_point(serialize_to_array_start:Point)
  // required int32 x = 1;
  if (has_x()) {
    target = ::google::protobuf::internal::WireFormatLite::WriteInt32ToArray(1, this->x(), target);
  }

  // required int32 y = 2;
  if (has_y()) {
    target = ::google::protobuf::internal::WireFormatLite::WriteInt32ToArray(2, this->y(), target);
  }

  // required float distance = 3;
  if (has_distance()) {
    target = ::google::protobuf::internal::WireFormatLite::WriteFloatToArray(3, this->distance(), target);
  }

  // required float angle = 4;
  if (has_angle()) {
    target = ::google::protobuf::internal::WireFormatLite::WriteFloatToArray(4, this->angle(), target);
  }

  // required bool end = 5;
  if (has_end()) {
    target = ::google::protobuf::internal::WireFormatLite::WriteBoolToArray(5, this->end(), target);
  }

  if (_internal_metadata_.have_unknown_fields()) {
    target = ::google::protobuf::internal::WireFormat::SerializeUnknownFieldsToArray(
        unknown_fields(), target);
  }
  // @@protoc_insertion_point(serialize_to_array_end:Point)
  return target;
}

int Point::RequiredFieldsByteSizeFallback() const {
// @@protoc_insertion_point(required_fields_byte_size_fallback_start:Point)
  int total_size = 0;

  if (has_x()) {
    // required int32 x = 1;
    total_size += 1 +
      ::google::protobuf::internal::WireFormatLite::Int32Size(
        this->x());
  }

  if (has_y()) {
    // required int32 y = 2;
    total_size += 1 +
      ::google::protobuf::internal::WireFormatLite::Int32Size(
        this->y());
  }

  if (has_distance()) {
    // required float distance = 3;
    total_size += 1 + 4;
  }

  if (has_angle()) {
    // required float angle = 4;
    total_size += 1 + 4;
  }

  if (has_end()) {
    // required bool end = 5;
    total_size += 1 + 1;
  }

  return total_size;
}
int Point::ByteSize() const {
// @@protoc_insertion_point(message_byte_size_start:Point)
  int total_size = 0;

  if (((_has_bits_[0] & 0x0000001f) ^ 0x0000001f) == 0) {  // All required fields are present.
    // required int32 x = 1;
    total_size += 1 +
      ::google::protobuf::internal::WireFormatLite::Int32Size(
        this->x());

    // required int32 y = 2;
    total_size += 1 +
      ::google::protobuf::internal::WireFormatLite::Int32Size(
        this->y());

    // required float distance = 3;
    total_size += 1 + 4;

    // required float angle = 4;
    total_size += 1 + 4;

    // required bool end = 5;
    total_size += 1 + 1;

  } else {
    total_size += RequiredFieldsByteSizeFallback();
  }
  if (_internal_metadata_.have_unknown_fields()) {
    total_size +=
      ::google::protobuf::internal::WireFormat::ComputeUnknownFieldsSize(
        unknown_fields());
  }
  GOOGLE_SAFE_CONCURRENT_WRITES_BEGIN();
  _cached_size_ = total_size;
  GOOGLE_SAFE_CONCURRENT_WRITES_END();
  return total_size;
}

void Point::MergeFrom(const ::google::protobuf::Message& from) {
// @@protoc_insertion_point(generalized_merge_from_start:Point)
  if (GOOGLE_PREDICT_FALSE(&from == this)) {
    ::google::protobuf::internal::MergeFromFail(__FILE__, __LINE__);
  }
  const Point* source = 
      ::google::protobuf::internal::DynamicCastToGenerated<const Point>(
          &from);
  if (source == NULL) {
  // @@protoc_insertion_point(generalized_merge_from_cast_fail:Point)
    ::google::protobuf::internal::ReflectionOps::Merge(from, this);
  } else {
  // @@protoc_insertion_point(generalized_merge_from_cast_success:Point)
    MergeFrom(*source);
  }
}

void Point::MergeFrom(const Point& from) {
// @@protoc_insertion_point(class_specific_merge_from_start:Point)
  if (GOOGLE_PREDICT_FALSE(&from == this)) {
    ::google::protobuf::internal::MergeFromFail(__FILE__, __LINE__);
  }
  if (from._has_bits_[0 / 32] & (0xffu << (0 % 32))) {
    if (from.has_x()) {
      set_x(from.x());
    }
    if (from.has_y()) {
      set_y(from.y());
    }
    if (from.has_distance()) {
      set_distance(from.distance());
    }
    if (from.has_angle()) {
      set_angle(from.angle());
    }
    if (from.has_end()) {
      set_end(from.end());
    }
  }
  if (from._internal_metadata_.have_unknown_fields()) {
    mutable_unknown_fields()->MergeFrom(from.unknown_fields());
  }
}

void Point::CopyFrom(const ::google::protobuf::Message& from) {
// @@protoc_insertion_point(generalized_copy_from_start:Point)
  if (&from == this) return;
  Clear();
  MergeFrom(from);
}

void Point::CopyFrom(const Point& from) {
// @@protoc_insertion_point(class_specific_copy_from_start:Point)
  if (&from == this) return;
  Clear();
  MergeFrom(from);
}

bool Point::IsInitialized() const {
  if ((_has_bits_[0] & 0x0000001f) != 0x0000001f) return false;

  return true;
}

void Point::Swap(Point* other) {
  if (other == this) return;
  InternalSwap(other);
}
void Point::InternalSwap(Point* other) {
  std::swap(x_, other->x_);
  std::swap(y_, other->y_);
  std::swap(distance_, other->distance_);
  std::swap(angle_, other->angle_);
  std::swap(end_, other->end_);
  std::swap(_has_bits_[0], other->_has_bits_[0]);
  _internal_metadata_.Swap(&other->_internal_metadata_);
  std::swap(_cached_size_, other->_cached_size_);
}

::google::protobuf::Metadata Point::GetMetadata() const {
  protobuf_AssignDescriptorsOnce();
  ::google::protobuf::Metadata metadata;
  metadata.descriptor = Point_descriptor_;
  metadata.reflection = Point_reflection_;
  return metadata;
}

#if PROTOBUF_INLINE_NOT_IN_HEADERS
// Point

// required int32 x = 1;
bool Point::has_x() const {
  return (_has_bits_[0] & 0x00000001u) != 0;
}
void Point::set_has_x() {
  _has_bits_[0] |= 0x00000001u;
}
void Point::clear_has_x() {
  _has_bits_[0] &= ~0x00000001u;
}
void Point::clear_x() {
  x_ = 0;
  clear_has_x();
}
 ::google::protobuf::int32 Point::x() const {
  // @@protoc_insertion_point(field_get:Point.x)
  return x_;
}
 void Point::set_x(::google::protobuf::int32 value) {
  set_has_x();
  x_ = value;
  // @@protoc_insertion_point(field_set:Point.x)
}

// required int32 y = 2;
bool Point::has_y() const {
  return (_has_bits_[0] & 0x00000002u) != 0;
}
void Point::set_has_y() {
  _has_bits_[0] |= 0x00000002u;
}
void Point::clear_has_y() {
  _has_bits_[0] &= ~0x00000002u;
}
void Point::clear_y() {
  y_ = 0;
  clear_has_y();
}
 ::google::protobuf::int32 Point::y() const {
  // @@protoc_insertion_point(field_get:Point.y)
  return y_;
}
 void Point::set_y(::google::protobuf::int32 value) {
  set_has_y();
  y_ = value;
  // @@protoc_insertion_point(field_set:Point.y)
}

// required float distance = 3;
bool Point::has_distance() const {
  return (_has_bits_[0] & 0x00000004u) != 0;
}
void Point::set_has_distance() {
  _has_bits_[0] |= 0x00000004u;
}
void Point::clear_has_distance() {
  _has_bits_[0] &= ~0x00000004u;
}
void Point::clear_distance() {
  distance_ = 0;
  clear_has_distance();
}
 float Point::distance() const {
  // @@protoc_insertion_point(field_get:Point.distance)
  return distance_;
}
 void Point::set_distance(float value) {
  set_has_distance();
  distance_ = value;
  // @@protoc_insertion_point(field_set:Point.distance)
}

// required float angle = 4;
bool Point::has_angle() const {
  return (_has_bits_[0] & 0x00000008u) != 0;
}
void Point::set_has_angle() {
  _has_bits_[0] |= 0x00000008u;
}
void Point::clear_has_angle() {
  _has_bits_[0] &= ~0x00000008u;
}
void Point::clear_angle() {
  angle_ = 0;
  clear_has_angle();
}
 float Point::angle() const {
  // @@protoc_insertion_point(field_get:Point.angle)
  return angle_;
}
 void Point::set_angle(float value) {
  set_has_angle();
  angle_ = value;
  // @@protoc_insertion_point(field_set:Point.angle)
}

// required bool end = 5;
bool Point::has_end() const {
  return (_has_bits_[0] & 0x00000010u) != 0;
}
void Point::set_has_end() {
  _has_bits_[0] |= 0x00000010u;
}
void Point::clear_has_end() {
  _has_bits_[0] &= ~0x00000010u;
}
void Point::clear_end() {
  end_ = false;
  clear_has_end();
}
 bool Point::end() const {
  // @@protoc_insertion_point(field_get:Point.end)
  return end_;
}
 void Point::set_end(bool value) {
  set_has_end();
  end_ = value;
  // @@protoc_insertion_point(field_set:Point.end)
}

#endif  // PROTOBUF_INLINE_NOT_IN_HEADERS

// @@protoc_insertion_point(namespace_scope)

// @@protoc_insertion_point(global_scope)
